/**
 * The name of the module
 */
module example {
    // should always require base.plugin
    requires base.plugin;
    // should always provide an implementation of Plugin
    provides fr.unice.miage.game.Plugin with fr.example.ExamplePlugin;
}