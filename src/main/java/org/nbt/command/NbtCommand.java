package org.nbt.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.item.ItemStack;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.core.command.annotations.CommandExecutor;

import javax.tools.Tool;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;

public class NbtCommand extends Command {

    public NbtCommand() {
        super("nbt", "View copy or paste NBT tags of held items");
    }

    @CommandExecutor(subCommand = "view")
    @CommandExecutor.Argument("raw")
    private String viewNbt(Optional<String> raw) {
        if (mc.player == null) {
            return "[NBTVIEWER] you are not an player";
        }
        ItemStack currentItem = mc.player.getMainHandItem();
        if (currentItem == null) {
            return "[NBTVIEWER] no held item";
        }
        CompoundTag itemNBT = currentItem.getTag();

        if (itemNBT.isEmpty()) {
            return "[NBTVIEWER] held item has no nbt data";
        }
        if (raw.isEmpty()) {
            return "[NBTVIEWER]\n" + NbtUtils.prettyPrint(itemNBT);
        }
        return "[NBTVIEWER]\n" + itemNBT.toString();
    }
    @CommandExecutor(subCommand = "copy")
    @CommandExecutor.Argument("pretty")
    private String copyNbt(Optional<String> pretty) {
        if (mc.player == null) {
            return "[NBTVIEWER] you are not an player";
        }
        ItemStack currentItem = mc.player.getMainHandItem();

        if (currentItem == null) {
            return "[NBTVIEWER] no held item";
        }
        CompoundTag itemNBT = currentItem.getTag();
        if (itemNBT == null) {
            return "[NBTVIEWER] held item has no nbt data";
        }
        if (pretty.isEmpty()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new StringSelection(
                            itemNBT.toString()
                    ), null
            );
            return "[NBTVIEWER] copied nbt data to clipboard";
        }
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(
                        NbtUtils.prettyPrint(itemNBT)
                ), null
        );
        return "[NBTVIEWER] copied pretty nbt data to clipboard";
    }
    @CommandExecutor(subCommand = "paste")
    private String pasteNbt() {
        if (mc.player == null) {
            return "[NBTVIEWER] you are not an player";
        }
        try {
            String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            CompoundTag nbt = TagParser.parseTag(clipboard);
            ItemStack currentItem = mc.player.getMainHandItem();
            if (currentItem == null) {
                return "[NBTVIEWER] no held item";
            }
            currentItem.setTag(nbt);
            return "[NBTVIEWER] pasted nbt data from clipboard";
        } catch (UnsupportedFlavorException | IOException | CommandSyntaxException e) {
            return "[NBTVIEWER] error pasting nbt data from clipboard";
        }
    }
    @CommandExecutor(subCommand = "clear")
    private String clearNbt() {
        if (mc.player == null) {
            return "[NBTVIEWER] you are not an player";
        }
        ItemStack currentItem = mc.player.getMainHandItem();
        if (currentItem == null) {
            return "[NBTVIEWER] no held item";
        }
        currentItem.setTag(new CompoundTag());
        return "[NBTVIEWER] cleared nbt data";
    }
    @CommandExecutor(subCommand = "add")
    @CommandExecutor.Argument("string")
    private String addNbt(String nbt) {
        if (mc.player == null) {
            return "[NBTVIEWER] you are not an player";
        }
        ItemStack currentItem = mc.player.getMainHandItem();
        if (currentItem == null) {
            return "[NBTVIEWER] no held item";
        }
        try {
            CompoundTag itemNBT = currentItem.getTag();
            CompoundTag newNBT = TagParser.parseTag(nbt);
            assert itemNBT != null;
            itemNBT.merge(newNBT);
            currentItem.setTag(itemNBT);
            return "[NBTVIEWER] added nbt data";
        } catch (CommandSyntaxException e) {
            return "[NBTVIEWER] error adding nbt data";
        }
    }
    @CommandExecutor(subCommand = "merge")
    private String mergeNbt() {
        if (mc.player == null) {
            return "[NBTVIEWER] you are not an player";
        }
        ItemStack currentItem = mc.player.getMainHandItem();
        if (currentItem == null) {
            return "[NBTVIEWER] no held item";
        }
        try {
            String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            CompoundTag nbt = TagParser.parseTag(clipboard);
            CompoundTag itemNBT = currentItem.getTag();
            assert itemNBT != null;
            itemNBT.merge(nbt);
            currentItem.setTag(itemNBT);
            return "[NBTVIEWER] merged nbt data";
        } catch (UnsupportedFlavorException | IOException | CommandSyntaxException e) {
            return "[NBTVIEWER] error merging nbt data";
        }
    }

}
