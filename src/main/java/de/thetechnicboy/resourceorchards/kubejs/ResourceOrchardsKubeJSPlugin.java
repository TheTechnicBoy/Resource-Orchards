package de.thetechnicboy.resourceorchards.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class ResourceOrchardsKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("ResourceOrchards", ResourceOrchardsKubeJS.class);
    }
}
