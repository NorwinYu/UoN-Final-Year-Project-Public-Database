package org.orcid.core.utils.v3.identifiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.mockito.Mockito;
import org.orcid.core.utils.v3.identifiers.resolvers.MetadataResolver;
import org.orcid.jaxb.model.common.Relationship;
import org.orcid.jaxb.model.common.WorkType;
import org.orcid.jaxb.model.v3.release.record.ExternalID;
import org.orcid.jaxb.model.v3.release.record.ExternalIDs;
import org.orcid.jaxb.model.v3.release.record.Work;
import org.orcid.pojo.PIDResolutionResult;
import org.springframework.test.util.ReflectionTestUtils;

//@RunWith(OrcidJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:orcid-core-context.xml" })
public class PIDResolverServiceTest {

    @Resource
    PIDResolverService resolverService;

    public void checkFail(String type, String value) {
        PIDResolutionResult r = resolverService.resolve(type, value);
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertFalse(r.isValidFormat());
        assertNull(r.getGeneratedUrl());
    }

    public void checkFailedResolutionButValidFormat(String type, String value) {
        PIDResolutionResult r = resolverService.resolve(type, value);
        assertFalse(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertNull(r.getGeneratedUrl());
    }

    // Commented out. Only use locally.
    // @Test
    public void workingTests() {
        PIDResolutionResult r = null;

        // Missing id
        checkFail("doi", "");

        // id without prefix and no normalizer, random value
        r = resolverService.resolve("agr", "word");
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertNull(r.getGeneratedUrl());

        // id without prefix and no normalizer, url value
        r = resolverService.resolve("agr", "https://orcid.org");
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertEquals("https://orcid.org", r.getGeneratedUrl());

        // id without prefix with normalizer
        r = resolverService.resolve("issn", "issn:1234-5678");
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertNull(r.getGeneratedUrl());

        // invalid id without prefix with normalizer
        checkFail("issn", "junk");

        // uri that's a url
        r = resolverService.resolve("uri", "http://orcid.org");
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertEquals("http://orcid.org", r.getGeneratedUrl());

        // uri that's a urn
        r = resolverService.resolve("uri", "urn:mace:whatever");
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertNull(r.getGeneratedUrl());

        // uri that's junk
        r = resolverService.resolve("uri", "junk");
        assertFalse(r.isResolved());
        assertFalse(r.getAttemptedResolution());
        assertTrue(r.isValidFormat()); // because we do not normalise this
                                       // field!
        assertNull(r.getGeneratedUrl());

        // TYPES WE WORK WITH
        r = resolverService.resolve("doi", "10.6084/m9.figshare.5479792.v1");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertEquals("https://doi.org/10.6084/m9.figshare.5479792.v1", r.getGeneratedUrl());

        r = resolverService.resolve("doi", "http://dx.doi.org/10.6084/m9.figshare.5479792.v1");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertTrue(r.isValidFormat());
        assertEquals("https://doi.org/10.6084/m9.figshare.5479792.v1", r.getGeneratedUrl());

        checkFailedResolutionButValidFormat("doi", "10.1234/DOES_NOT_EXIST_404");

        r = resolverService.resolve("isbn", "009177909X");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://www.worldcat.org/isbn/009177909X", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        r = resolverService.resolve("isbn", "isbn:009177909X");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://www.worldcat.org/isbn/009177909X", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFailedResolutionButValidFormat("isbn", "0091779091");
        checkFail("isbn", "9780091779093XXXXX");

        r = resolverService.resolve("pmc", "PMC3820882");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://europepmc.org/articles/PMC3820882", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        r = resolverService.resolve("pmc", "3820882");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://europepmc.org/articles/PMC3820882", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFailedResolutionButValidFormat("pmc", "99999999");
        checkFail("pmc", "junk");

        // RRID
        r = resolverService.resolve("rrid", "RRID:AB_2203913");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://identifiers.org/rrid/RRID:AB_2203913", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        r = resolverService.resolve("rrid", "AB_2203913");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://identifiers.org/rrid/RRID:AB_2203913", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFailedResolutionButValidFormat("rrid", "whatever");

        // PMID:
        r = resolverService.resolve("pmid", "22791631");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://www.ncbi.nlm.nih.gov/pubmed/22791631", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        r = resolverService.resolve("pmid", "PMID:22791631");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://www.ncbi.nlm.nih.gov/pubmed/22791631", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFail("pmid", "junk");
        checkFailedResolutionButValidFormat("pmid", "99999999");

        // ARXIV
        r = resolverService.resolve("arxiv", "1802.05783");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://arxiv.org/abs/1802.05783", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        r = resolverService.resolve("arxiv", "https://arxiv.org/abs/1802.05783");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://arxiv.org/abs/1802.05783", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFail("arxiv", "junk");
        checkFailedResolutionButValidFormat("arxiv", "0000.00000");

        // PDB
        r = resolverService.resolve("pdb", "5N7R");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("http://identifiers.org/pdb/5n7r", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFailedResolutionButValidFormat("pdb", "junkyjunk"); // needs
                                                                 // normalizer?

        // rfc http://www.rfc-editor.org/rfc/
        r = resolverService.resolve("rfc", "63");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://tools.ietf.org/html/63", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        r = resolverService.resolve("rfc", "rfc0063");
        assertTrue(r.isResolved());
        assertTrue(r.getAttemptedResolution());
        assertEquals("https://tools.ietf.org/html/rfc0063", r.getGeneratedUrl());
        assertTrue(r.isValidFormat());

        checkFailedResolutionButValidFormat("rfc", "junkMcJunk"); // needs
                                                                  // normalizer?
        // FAILS---------
        // BIBCODE
        /*
         * r = resolver.resolve("bibcode", "1974AJ.....79..819H");
         * assertTrue(r.isResolved()); assertTrue(r.getAttemptedResolution());
         * assertEquals("http://adsabs.harvard.edu/abs/1974AJ.....79..819H",r.
         * getGeneratedUrl()); assertTrue(r.isValidFormat());
         * 
         * r = resolver.resolve("bibcode", "bibcode:1974AJ.....79..819H");
         * assertTrue(r.isResolved()); assertTrue(r.getAttemptedResolution());
         * assertEquals("http://adsabs.harvard.edu/abs/1974AJ.....79..819H",r.
         * getGeneratedUrl()); assertTrue(r.isValidFormat());
         * 
         * checkFail("bibcode","junk");
         * //checkFailedResolutionButValidFormat("bibcode","123456789.A23456789"
         * ); //this is not reliable 404. Sometimes 200.weird!
         *
         */

        // HEAD fails, GET works
        /*
         * r = resolver.resolve("cienciaiul", "ci-pub-42661");
         * assertTrue(r.isResolved());
         * assertEquals("https://ciencia.iscte-iul.pt/id/ci-pub-42661",r.
         * getResolvedUrl()); r = resolver.resolve("lensid",
         * "022-382-024-804-703"); assertTrue(r.isResolved());
         * assertEquals("https://www.lens.org/022-382-024-804-703",r.
         * getResolvedUrl());
         */

        // FAILS without fake useragent
        // curl -sSLI -A "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac
        // OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2
        // Mobile/8J2 Safari/6533.18.5" -D -
        // http://www.jstor.org/stable/41670578 -o /dev/null
        /*
         * r = resolver.resolve("jstor", "41670578");
         * assertTrue(r.isResolved());
         * assertEquals("http://www.jstor.org/stable/41670578",r.getResolvedUrl(
         * ));
         */

        // Works in curl, fails in Java
        /*
         * r = resolver.resolve("ssrn", "2900690"); assertTrue(r.isResolved());
         * assertEquals("http://papers.ssrn.com/abstract_id=2900690",r.
         * getResolvedUrl());
         */

        // should be https://tools.ietf.org/html/0063
        /*
         * r = resolver.resolve("RFC", "0063"); assertTrue(r.isResolved());
         * assertEquals("http://www.rfc-editor.org/rfc/0063",r.getResolvedUrl())
         * ;
         */

        /*
         * And these identifiers don't mark any text as invalid even if it is
         * gibberish: ethos, jfm, kuid, lccn, mr, oclc, osti and zbl
         */

        // ----end fails

    }

    @Test
    public void testBookChapterAndISBNRelationship() {
        resolverService = new PIDResolverService();

        MetadataResolver resolver = Mockito.mock(MetadataResolver.class);
        Mockito.when(resolver.resolveMetadata(Mockito.eq("doi"), Mockito.eq("blah"))).thenReturn(getBookChapterWorkWithISBNExternalIdentifier());
        Map<String, LinkedList<MetadataResolver>> resolverMap = new HashMap<>();
        LinkedList<MetadataResolver> resolversList = new LinkedList<>();
        resolversList.add(resolver);
        resolverMap.put("doi", resolversList);
        ReflectionTestUtils.setField(resolverService, "metaResolverMap", resolverMap);

        Work work = resolverService.resolveMetadata("doi", "blah");
        assertEquals(WorkType.BOOK_CHAPTER, work.getWorkType());
        assertEquals(1, work.getExternalIdentifiers().getExternalIdentifier().size());
        assertEquals("isbn", work.getExternalIdentifiers().getExternalIdentifier().get(0).getType());

        // check pid resolver service has changed relationship to part_of
        assertEquals(Relationship.PART_OF, work.getExternalIdentifiers().getExternalIdentifier().get(0).getRelationship());
    }

    private Work getBookChapterWorkWithISBNExternalIdentifier() {
        Work work = new Work();
        work.setWorkType(WorkType.BOOK_CHAPTER);
        ExternalIDs externalIds = new ExternalIDs();
        ExternalID isbn = new ExternalID();
        isbn.setType("isbn");
        isbn.setRelationship(Relationship.SELF);
        isbn.setValue("9780123456789");
        externalIds.getExternalIdentifier().add(isbn);
        work.setWorkExternalIdentifiers(externalIds);
        return work;
    }

}