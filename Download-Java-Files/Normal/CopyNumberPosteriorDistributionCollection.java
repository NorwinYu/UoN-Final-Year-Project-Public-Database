package org.broadinstitute.hellbender.tools.copynumber.formats.collections;

import org.broadinstitute.hellbender.exceptions.UserException;
import org.broadinstitute.hellbender.tools.copynumber.formats.CopyNumberFormatsUtils;
import org.broadinstitute.hellbender.tools.copynumber.formats.records.CopyNumberPosteriorDistribution;
import org.broadinstitute.hellbender.tools.copynumber.gcnv.GermlineCNVNamingConstants;
import org.broadinstitute.hellbender.tools.copynumber.gcnv.IntegerCopyNumberState;
import org.broadinstitute.hellbender.utils.Utils;
import org.broadinstitute.hellbender.utils.tsv.DataLine;
import org.broadinstitute.hellbender.utils.tsv.TableColumnCollection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Collection of integer copy-number posteriors.
 *
 * @author Andrey Smirnov &lt;asmirnov@broadinstitute.org&gt;
 */
public final class CopyNumberPosteriorDistributionCollection extends AbstractSampleRecordCollection<CopyNumberPosteriorDistribution> {

    public CopyNumberPosteriorDistributionCollection(final File inputFile) {
        this(inputFile, new IntegerCopyNumberStateCollection(inputFile));
    }

    private CopyNumberPosteriorDistributionCollection(final File inputFile,
                                                      final IntegerCopyNumberStateCollection integerCopyNumberStateCollection) {
        super(inputFile,
                Utils.nonNull(integerCopyNumberStateCollection).getTableColumnCollection(),
                getPosteriorRecordFromDataLineDecoder(integerCopyNumberStateCollection),
                getPosteriorRecordToDataLineEncoder(integerCopyNumberStateCollection));
    }

    /**
     * Generates an instance of {@link CopyNumberPosteriorDistribution} from a {@link DataLine} entry read from
     * a copy-number posterior file generated by `gcnvkernel`.
     *
     * Note: we assume that the posteriors are stored in the log space.
     */
    private static Function<DataLine, CopyNumberPosteriorDistribution> getPosteriorRecordFromDataLineDecoder(
            final IntegerCopyNumberStateCollection integerCopyNumberStateCollection) {
            return dataLine -> {
                try {
                    return new CopyNumberPosteriorDistribution(
                            IntStream.range(0, integerCopyNumberStateCollection.size())
                                    .mapToObj(integerCopyNumberStateCollection::get)
                                    .collect(Collectors.toMap(
                                            Function.identity(),
                                            state -> dataLine.getDouble(state.getCopyNumber()))));
                } catch (final IllegalArgumentException ex) {
                    throw new UserException.BadInput("Validation error occurred on line %d of the posterior file: "
                            + String.format(ex.getMessage(), dataLine.getLineNumber()));
                }
            };
    }

    /**
     * Generates an instance of {@link DataLine} from {@link CopyNumberPosteriorDistribution} for writing a posterior
     * collection to a file.
     *
     * Note: we store the posteriors in the log space
     */
    private static BiConsumer<CopyNumberPosteriorDistribution, DataLine> getPosteriorRecordToDataLineEncoder(
            final IntegerCopyNumberStateCollection integerCopyNumberStateCollection) {
        return (copyNumberPosteriorRecord, dataLine) ->
            integerCopyNumberStateCollection.getCopyNumberStates()
                    .forEach(state -> dataLine.append(copyNumberPosteriorRecord.getCopyNumberPosterior(state)));
    }

    /**
     * Collection of integer copy-number states and copy-number table columns constructed from
     * the header of a TSV file.
     */
    private static class IntegerCopyNumberStateCollection {

        private final List<IntegerCopyNumberState> copyNumberStates;
        private final TableColumnCollection columnCollection;

        IntegerCopyNumberStateCollection(final File inputFile) {
            this.columnCollection = CopyNumberFormatsUtils.readColumnsFromHeader(inputFile);
            this.copyNumberStates = new ArrayList<>();
            columnCollection.names()
                    .forEach(copyNumberString -> copyNumberStates.add(parseIntegerCopyNumber(copyNumberString)));
        }

        /**
         * Get list of all copy number states in this collection
         */
        List<IntegerCopyNumberState> getCopyNumberStates() {
            return copyNumberStates;
        }

        /**
         * Get copy number state given an its index
         */
        IntegerCopyNumberState get(final int index) {
            return copyNumberStates.get(index);
        }

        /**
         * Get the number of states in this collection
         */
        int size() {
            return columnCollection.columnCount();
        }

        /**
         * Get the corresponding {@link TableColumnCollection} for this copy number state collection
         */
        TableColumnCollection getTableColumnCollection() {
            return columnCollection;
        }

        private IntegerCopyNumberState parseIntegerCopyNumber(final String copyNumberStateString) {
            if (!copyNumberStateString.startsWith(GermlineCNVNamingConstants.COPY_NUMBER_TABLE_COLUMN_PREFIX)) {
                throw new UserException.BadInput(String.format("The column names of the copy number posterior file must " +
                        "start with %s.", GermlineCNVNamingConstants.COPY_NUMBER_TABLE_COLUMN_PREFIX));
            }
            final String integerCopyNumberStateString = copyNumberStateString.substring(
                    GermlineCNVNamingConstants.COPY_NUMBER_TABLE_COLUMN_PREFIX.length());
            try {
                final int copyNumber = Integer.parseInt(integerCopyNumberStateString);
                return new IntegerCopyNumberState(copyNumber);
            } catch (final NumberFormatException e) {
                throw new UserException.BadInput(String.format(
                        "Could not parse copy-number column string (%s) to an integer copy-number.", copyNumberStateString));
            }
        }
    }
}