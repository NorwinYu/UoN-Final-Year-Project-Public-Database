package nwoolcan.controller.batch;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationFactory;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepDTO;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDTO;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import nwoolcan.viewmodel.brewery.production.batch.StockBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of {@link BatchController} interface.
 */
public final class BatchControllerImpl implements BatchController {

    private final Brewery model;
    private final StepController stepController;

    /**
     * Basic constructor with reference to the {@link Brewery} model.
     * @param model the model to use as reference.
     */
    public BatchControllerImpl(final Brewery model) {
        this.model = model;

        this.stepController = new StepControllerImpl(this.model);
    }

    @Override
    public StepController getStepController() {
        return this.stepController;
    }

    @Override
    public Result<DetailBatchViewModel> getDetailBatchViewModelById(final int batchId) {
        return this.model.getBatchById(batchId)
                         .map(DetailBatchViewModel::new);
    }

    @Override
    public Result<GoNextStepViewModel> getGoNextStepViewModel(final int batchId) {
        return this.model.getBatchById(batchId)
                         .map(GoNextStepViewModel::new);
    }

    @Override
    public Result<Empty> goToNextStep(final int batchId, final GoNextStepDTO dto) {
        Result<Batch> res = this.model.getBatchById(batchId);

        if (dto.finalizeBeforeGoingToNext()) {
            res = res.flatMap(b -> {
                         //did this for nullaway
                         if (dto.getEndSize() == null) {
                             return Result.error(new IllegalArgumentException());
                         }
                         return b.getCurrentStep().finalize(
                             dto.getNotes().isEmpty() ? null : dto.getNotes(),
                             new Date(),
                             dto.getEndSize()).map(e -> b);
                     });
        }

        return res.flatMap(b -> b.moveToNextStep(dto.getNextStepType()));
    }

    @Override
    public Result<Empty> addBatchEvaluation(final int batchID, final BatchEvaluationDTO newBatch) {
        return newBatch.getCategories()
                       .stream()
                       .map(cat -> EvaluationFactory.create(cat.getLeft(), cat.getMiddle(), cat.getRight().orElse(null)))
            .<Result<Set<Evaluation>>>reduce(
                Result.of(new HashSet<>()),
                (res, cat) -> res.require(cat::isPresent, cat::getError)
                                 .peek(list -> list.add(cat.getValue())),
                (res1, res2) -> res1.require(res2::isPresent, res2::getError)
                                    .peek(list -> list.addAll(res2.orElse(HashSet::new))))
            .flatMap(cat -> {
                BatchEvaluationBuilder builder = new BatchEvaluationBuilder();
                newBatch.getNotes().ifPresent(builder::addNotes);
                newBatch.getReviewer().ifPresent(builder::addReviewer);
                return builder.build(newBatch.getBatchEvaluationType(), cat);
            })
            .flatMap(eval -> model.getBatchById(batchID).flatMap(b -> b.setEvaluation(eval)));
    }

    @Override
    public Result<Empty> checkEvaluation(final EvaluationType type, final int score, final Optional<String> notes) {
        return EvaluationFactory.create(type, score, notes.orElse(null)).toEmpty();
    }

    @Override
    public Result<Optional<BatchEvaluationDetailViewModel>> getBatchEvaluation(final int batchID) {
        return model.getBatchById(batchID).map(batch -> batch.getEvaluation().map(BatchEvaluationDetailViewModel::new));
    }

    @Override
    public Result<Set<BatchEvaluationType>> getAvailableBatchEvaluationTypes() {
        return BatchEvaluationBuilder.getAvailableBatchEvaluationTypes();
    }

    @Override
    public Result<StockBatchViewModel> getStockBatchViewModel(final int batchId) {
        Result<Batch> res = this.model.getBatchById(batchId);

        List<BeerArticleViewModel> beerArticles = this.model.getWarehouse().getArticles(new QueryArticleBuilder().setIncludeArticleType(ArticleType.FINISHED_BEER)
                                                                                                                 .build())
                                                            .stream()
                                                            .filter(a -> res.map(b -> b.getCurrentSize()
                                                                                       .getUnitOfMeasure()
                                                                                       .equals(a.getUnitOfMeasure()))
                                                                            .orElse(false))
                                                            .map(a -> a.toBeerArticle().getValue())
                                                            .map(BeerArticleViewModel::new)
                                                            .collect(Collectors.toList());

        return res.map(b -> new StockBatchViewModel(batchId, b.getCurrentSize().getUnitOfMeasure(), beerArticles));
    }
}
