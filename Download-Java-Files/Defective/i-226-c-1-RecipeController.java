package com.lumatik.RecipeManagerREST.controller;

import com.lumatik.RecipeManagerREST.model.Recipe;
import com.lumatik.RecipeManagerREST.error.RecipeNotFoundException;
import com.lumatik.RecipeManagerREST.model.RecipeResourceAssembler;
import com.lumatik.RecipeManagerREST.model.Status;
import com.lumatik.RecipeManagerREST.repository.RecipeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/recipes", produces = "application/json")
@Api(value = "recipemanager", description = "Operations pertaining to recipes in Recipe Manager application")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeResourceAssembler recipeResourceAssembler;

    @ApiOperation(value = "View a list of recipes", response = Resources.class)
    @GetMapping("/")
    // Resources<T> - Spring HATEOAS container that encapsulates collections of type T Resources
    // :: is Java 8 syntax for a method reference
    public Resources<Resource<Recipe>> all() {
        List<Resource<Recipe>> recipes = recipeRepository.findAll().stream()
                .map(recipeResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(recipes,
                linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }

    @ApiOperation(value = "Add a recipe", response = ResponseEntity.class)
    @PostMapping("/")
    ResponseEntity<?> newRecipe(@RequestBody Recipe newRecipe) throws URISyntaxException {
        Resource<Recipe> recipeResource = recipeResourceAssembler
                .toResource(recipeRepository.save(newRecipe));

        // ResponseEntity creates a HTTP 201 Created status message
        return ResponseEntity
                .created(new URI(recipeResource.getId().expand().getHref()))
                .body(recipeResource);
    }

    @ApiOperation(value = "View a product by its id", response = Resource.class)
    @GetMapping("/{id}")
    public Resource<Recipe> one(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        return recipeResourceAssembler.toResource(recipe);
    }

    @ApiOperation(value = "Update a recipe", response = Recipe.class)
    @PutMapping("/{id}")
    Recipe replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {

        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(newRecipe.getTitle());
                    recipe.setDescription(newRecipe.getDescription());
                    return recipeRepository.save(recipe);

                    // TODO - return ResponseEntity.ok
                })
                .orElseGet(() -> {
                    newRecipe.setId(id);
                    return recipeRepository.save(newRecipe);
                });
    }

    @ApiOperation(value = "Delete a recipe", response = ResponseEntity.class)
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeRepository.deleteById(id);

        // Response Entity returns a 204 No Content response
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Archive a recipe", response = ResponseEntity.class)
    @DeleteMapping("/{id}/archive")
    public ResponseEntity<ResourceSupport> archive(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        if (recipe.getStatus() == Status.ACTIVE) {
            recipe.setStatus(Status.ARCHIVED);
            return ResponseEntity.ok(recipeResourceAssembler
                    .toResource(recipeRepository.save(recipe)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed",
                        "You cannot archive a recipe that is already archived."));
    }

    @ApiOperation(value = "Unarchive a recipe", response = ResponseEntity.class)
    @PutMapping("/{id}/unarchive")
    public ResponseEntity<ResourceSupport> unarchive(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        if (recipe.getStatus() == Status.ARCHIVED) {
            recipe.setStatus(Status.ACTIVE);
            return ResponseEntity.ok(recipeResourceAssembler
                    .toResource(recipeRepository.save(recipe)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed",
                        "You cannot unarchive a recipe that is already active."));
    }
}
