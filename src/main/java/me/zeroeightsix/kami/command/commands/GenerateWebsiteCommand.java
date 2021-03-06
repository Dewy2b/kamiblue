package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static me.zeroeightsix.kami.KamiMod.MODULE_MANAGER;

/**
 * @author S-B99
 * Updated by S-B99 on 18/03/20
 *
 * Horribly designed command for uh, generating the modules page on the website. This was the easiest way I could do it, but maybe not the most efficient.
 */
public class GenerateWebsiteCommand extends Command {
    public GenerateWebsiteCommand() {
        super("genwebsite", null);
        setDescription("Generates the module page for the website");
    }

    private static String nameAndDescription(Module module) {
        return "<li>" + module.getName() + "<p><i>" + module.getDescription() + "</i></p></li>";
    }

    @Override
    public void call(String[] args) {
        List<Module> mods = MODULE_MANAGER.getModules().stream().filter(Module::isProduction).collect(Collectors.toList());
        String[] modCategories = new String[]{"Chat", "Combat", "Client", "Misc", "Movement", "Player", "Render", "Utils"};
        List<String> modCategoriesList = new ArrayList<>(java.util.Arrays.asList(modCategories));

        List<String> modsChat = new ArrayList<>();
        List<String> modsCombat = new ArrayList<>();
        List<String> modsClient = new ArrayList<>();
        List<String> modsMisc = new ArrayList<>();
        List<String> modsMovement = new ArrayList<>();
        List<String> modsPlayer = new ArrayList<>();
        List<String> modsRender = new ArrayList<>();
        List<String> modsUtils = new ArrayList<>();

        mods.forEach(module -> {
            switch (module.getCategory()) {
                case CHAT:
                    modsChat.add(nameAndDescription(module));
                case COMBAT:
                    modsCombat.add(nameAndDescription(module));
                case CLIENT:
                    modsClient.add(nameAndDescription(module));
                case MISC:
                    modsMisc.add(nameAndDescription(module));
                case MOVEMENT:
                    modsMovement.add(nameAndDescription(module));
                case PLAYER:
                    modsPlayer.add(nameAndDescription(module));
                case RENDER:
                    modsRender.add(nameAndDescription(module));
                case UTILS:
                    modsUtils.add(nameAndDescription(module));
            }
        });

        KamiMod.log.info("\n"
                + "---\n"
                + "layout: default\n"
                + "title: Modules\n"
                + "description: A list of modules and commands this mod has\n"
                + "---"
                + "\n## Modules (" + mods.size() + ")\n");

        modCategoriesList.forEach(modCategory -> {
            int totalMods;
            totalMods = (int) mods.stream().filter(module -> module.getCategory().toString().equalsIgnoreCase(modCategory)).count();
            KamiMod.log.info("<details>");
            KamiMod.log.info("    <summary>" + modCategory + " (" + totalMods + ")</summary>");
            KamiMod.log.info("    <p><ul>");
            mods.forEach(module -> {
                if (module.getCategory().toString().equalsIgnoreCase(modCategory)) {
                    KamiMod.log.info("        <li>" + module.getName() + "<p><i>" + module.getDescription() + "</i></p></li>");
                }
            });
            KamiMod.log.info("    </ul></p>");
            KamiMod.log.info("</details>");

        });

        KamiMod.log.info("\n## Commands (" + KamiMod.getInstance().getCommandManager().getCommands().size() + ")\n");

        KamiMod.getInstance().getCommandManager().getCommands().stream().sorted(Comparator.comparing(Command::getLabel)).forEach(command -> {
            KamiMod.log.info("<details>");
            KamiMod.log.info("    <summary>" + command.getLabel() + "</summary>");
            KamiMod.log.info("    <p><ul>");
            KamiMod.log.info("        <li>" + command.getDescription() + "<p><i>Aliases: " + command.getAliases() + "</i></p></li>");
            KamiMod.log.info("    </ul></p>");
            KamiMod.log.info("</details>");
        });

        Command.sendChatMessage(getLabel().substring(0, 1).toUpperCase() + getLabel().substring(1) + ": Generated website to log file!");
    }
}
