package io.stormbird.wallet.router;


import android.content.Context;
import android.content.Intent;
import io.stormbird.token.tools.Convert;
import io.stormbird.wallet.C;
import io.stormbird.wallet.entity.ConfirmationType;
import io.stormbird.wallet.ui.ConfirmationActivity;
import io.stormbird.wallet.web3.entity.Web3Transaction;

import java.math.BigInteger;

//TODO: Refactor when we add token type to token class
public class ConfirmationRouter {
    public void open(Context context, String to, BigInteger amount, String contractAddress, int decimals, String symbol, boolean sendingTokens, String ensDetails, int chainId) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(C.EXTRA_TO_ADDRESS, to);
        intent.putExtra(C.EXTRA_AMOUNT, amount.toString());
        intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, contractAddress);
        intent.putExtra(C.EXTRA_DECIMALS, decimals);
        intent.putExtra(C.EXTRA_SYMBOL, symbol);
        intent.putExtra(C.EXTRA_SENDING_TOKENS, sendingTokens);
        intent.putExtra(C.EXTRA_ENS_DETAILS, ensDetails);
        intent.putExtra(C.EXTRA_NETWORKID, chainId);
        int tokenType = ConfirmationType.ETH.ordinal();
        if (sendingTokens) tokenType = ConfirmationType.ERC20.ordinal();
        intent.putExtra(C.TOKEN_TYPE, tokenType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void open(Context context, Web3Transaction transaction, String networkName, boolean isMainNet, String requesterURL, int chainId)
    {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(C.EXTRA_WEB3TRANSACTION, transaction);
        intent.putExtra(C.EXTRA_AMOUNT, Convert.fromWei(transaction.value.toString(10), Convert.Unit.WEI).toString());
        intent.putExtra(C.TOKEN_TYPE, ConfirmationType.WEB3TRANSACTION.ordinal());
        intent.putExtra(C.EXTRA_NETWORK_NAME, networkName);
        intent.putExtra(C.EXTRA_NETWORK_MAINNET, isMainNet);
        intent.putExtra(C.EXTRA_CONTRACT_NAME, requesterURL);
        intent.putExtra(C.EXTRA_NETWORKID, chainId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openERC721Transfer(Context context, String to, String tokenId, String contractAddress, String name, String tokenName, String ensDetails, int chainId)
    {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(C.EXTRA_TO_ADDRESS, to);
        intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, contractAddress);
        intent.putExtra(C.EXTRA_DECIMALS, 0);
        intent.putExtra(C.EXTRA_SYMBOL, tokenName);
        intent.putExtra(C.EXTRA_AMOUNT, tokenId);
        intent.putExtra(C.EXTRA_SENDING_TOKENS, true);
        intent.putExtra(C.TOKEN_TYPE, ConfirmationType.ERC721.ordinal());
        intent.putExtra(C.EXTRA_TOKENID_LIST, tokenId);
        intent.putExtra(C.EXTRA_CONTRACT_NAME, name);
        intent.putExtra(C.EXTRA_ENS_DETAILS, ensDetails);
        intent.putExtra(C.EXTRA_NETWORKID, chainId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
