package de.thetechnicboy.resourceorchards.jei;

import de.thetechnicboy.resourceorchards.ResourceOrchards;
import de.thetechnicboy.resourceorchards.tree.RegisteredTree;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ResourceOrchardsJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = ResourceOrchards.fromNamespaceAndPath("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new TreesCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration){
        List<RegisteredTree> trees = new ArrayList<>();
        TreeRegistry.getTrees().forEach((id, tree) -> {
            trees.add(tree);
        });

        registration.addRecipes(
                TreesCategory.RECIPE_TYPE,
                trees
        );
    }
}
