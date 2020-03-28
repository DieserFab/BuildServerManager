package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayerUtilitiyMenu extends AbstractGui {
    public PlayerUtilitiyMenu(GuiType guiType, String title, String name) {
        super(guiType, title, name);
    }

    @Override
    public void init() {
        setItem(10,new ItemCreator("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ2ODM0M2JkMGIxMjlkZTkzY2M4ZDNiYmEzYjk3YTJmYWE3YWRlMzhkOGE2ZTJiODY0Y2Q4NjhjZmFiIn19fQ==",1, Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_0,null).create());
        setItem(11,new ItemCreator("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJhNmYwZTg0ZGFlZmM4YjIxYWE5OTQxNWIxNmVkNWZkYWE2ZDhkYzBjM2NkNTkxZjQ5Y2E4MzJiNTc1In19fQ==",1,Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_1,null).create());
        setItem(12,new ItemCreator("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZmYWI5OTFkMDgzOTkzY2I4M2U0YmNmNDRhMGI2Y2VmYWM2NDdkNDE4OWVlOWNiODIzZTljYzE1NzFlMzgifX19",1,Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_2,null).create());
        setItem(13,new ItemCreator("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QzMTliOTM0M2YxN2EzNTYzNmJjYmMyNmI4MTk2MjVhOTMzM2RlMzczNjExMWYyZTkzMjgyN2M4ZTc0OSJ9fX0=",1,Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_3,null).create());
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, Messages.GUIS_PLAYERUTILITYMENU_BACK, Messages.GUIS_PLAYERUTILITYMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {

    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String name = itemUsed.getItemMeta().getDisplayName();
        if(name.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_BACK)){
            BSM.getInstance().getGuiManager().getGui("mainmenu").openGui(player);
        }
        if(name.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_0)){
            player.performCommand("gamemode 0");
        }
        if(name.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_1)){
            player.performCommand("gamemode 1");
        }
        if(name.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_2)){
            player.performCommand("gamemode 2");
        }
        if(name.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_3)){
            player.performCommand("gamemode 3");
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
