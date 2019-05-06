package org.orcid.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.orcid.core.exception.UserAlreadyUsing2FAException;
import org.orcid.core.manager.BackupCodeManager;
import org.orcid.core.manager.EncryptionManager;
import org.orcid.core.manager.ProfileEntityCacheManager;
import org.orcid.core.manager.ProfileEntityManager;
import org.orcid.core.manager.TwoFactorAuthenticationManager;
import org.orcid.core.manager.read_only.EmailManagerReadOnly;
import org.orcid.jaxb.model.record_v2.Email;
import org.orcid.persistence.jpa.entities.ProfileEntity;

public class TwoFactorAuthenticationManagerImpl implements TwoFactorAuthenticationManager {

    private static final String APP_NAME = "orcid.org";
    
    @Resource
    private EncryptionManager encryptionManager;

    @Resource
    private EmailManagerReadOnly emailManagerReadOnly;

    @Resource
    private ProfileEntityManager profileEntityManager;

    @Resource
    private ProfileEntityCacheManager profileEntityCacheManager;
    
    @Resource
    private BackupCodeManager backupCodeManager;

    @Override
    public String getQRCode(String orcid) {
        if (userUsing2FA(orcid)) {
            // don't allow generation of new code if user already using
            throw new UserAlreadyUsing2FAException();
        }
        // generate secret but don't switch on using2FA - user may abort process
        String secret = Base32.random();
        profileEntityManager.update2FASecret(orcid, encryptionManager.encryptForInternalUse(secret));
        Email email = emailManagerReadOnly.findPrimaryEmail(orcid);
        //generatate URL for QR code per https://github.com/google/google-authenticator/wiki/Key-Uri-Format
        //do not URL encode - authenticator app throws error
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, email.getEmail(), secret, APP_NAME);
    }

    @Override
    public void enable2FA(String orcid) {
        profileEntityManager.enable2FA(orcid);
    }

    @Override
    public void disable2FA(String orcid) {
        profileEntityManager.disable2FA(orcid);
        backupCodeManager.removeUnusedBackupCodes(orcid);
    }

    @Override
    public boolean userUsing2FA(String orcid) {
        ProfileEntity profile = profileEntityCacheManager.retrieve(orcid);
        return profile.getUsing2FA();
    }

    @Override
    public boolean verificationCodeIsValid(String code, String orcid) {
        ProfileEntity profileEntity = profileEntityCacheManager.retrieve(orcid);
        return verificationCodeIsValid(code, profileEntity);
    }
    
    @Override
    public boolean verificationCodeIsValid(String code, ProfileEntity profileEntity) {
        code = code.replaceAll("\\s", "");
        if (!validLong(code)) {
            return false;
        }
        
        String decryptedSecret = encryptionManager.decryptForInternalUse(profileEntity.getSecretFor2FA());
        Totp totp = new Totp(decryptedSecret);
        return totp.verify(code.replaceAll("\\s", ""));
    }

    private boolean validLong(String code) {
        try {
            Long.parseLong(code);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<String> getBackupCodes(String orcid) {
        return backupCodeManager.createBackupCodes(orcid);
    }

    @Override
    public String getSecret(String orcid) {
        ProfileEntity profileEntity = profileEntityCacheManager.retrieve(orcid);
        return encryptionManager.decryptForInternalUse(profileEntity.getSecretFor2FA());
    }

}
