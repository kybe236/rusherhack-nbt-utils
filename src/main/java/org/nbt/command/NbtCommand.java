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
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();

            if (!currentItem.isEmpty()) {
                CompoundTag itemNBT = currentItem.getTag();

                if (!itemNBT.isEmpty()) {
                    if (!raw.isEmpty()){
                        return "[NBTVIEWER]\n" + itemNBT.toString();
                    } else {
                        return "[NBTVIEWER]\n" + NbtUtils.prettyPrint(itemNBT);
                    }
                }
                else {
                    return "[NBTVIEWER] held item has no nbt data";
                }
            } else {
                return "[NBTVIEWER] no held item";
            }
        }
        else {
            return "[NBTVIEWER] you are not an player";
        }
    }
    @CommandExecutor(subCommand = "copy")
    @CommandExecutor.Argument("pretty")
    private String copyNbt(Optional<String> pretty) {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();

            if (!currentItem.isEmpty()) {
                CompoundTag itemNBT = currentItem.getTag();
                if (!itemNBT.isEmpty()) {
                    if (!pretty.isEmpty()) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(NbtUtils.prettyPrint(itemNBT)), null);
                    } else {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(itemNBT.toString()), null);
                    }
                    return "[NBTVIEWER] copied nbt data to clipboard";
                } else {
                    return "[NBTVIEWER] held item has no nbt data";
                }
            } else {
                return "[NBTVIEWER] no held item";
            }

        } else {
            return "[NBTVIEWER] you are not an player";
        }
    }
    @CommandExecutor(subCommand = "paste")
    private String pasteNbt() {
        if (mc.player != null) {
            try {
                String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                CompoundTag nbt = TagParser.parseTag(clipboard);
                ItemStack currentItem = mc.player.getMainHandItem();
                if (!currentItem.isEmpty()) {
                    currentItem.setTag(nbt);
                    return "[NBTVIEWER] pasted nbt data from clipboard";
                } else {
                    return "[NBTVIEWER] no held item";
                }
            } catch (UnsupportedFlavorException | IOException | CommandSyntaxException e) {
                return "[NBTVIEWER] error pasting nbt data from clipboard";
            }
        } else {
            return "[NBTVIEWER] you are not an player";
        }
    }
    @CommandExecutor(subCommand = "clear")
    private String clearNbt() {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();
            if (!currentItem.isEmpty()) {
                currentItem.setTag(new CompoundTag());
                return "[NBTVIEWER] cleared nbt data";
            } else {
                return "[NBTVIEWER] no held item";
            }
        } else {
            return "[NBTVIEWER] you are not an player";
        }
    }
    @CommandExecutor(subCommand = "add")
    @CommandExecutor.Argument("string")
    private String addNbt(String nbt) {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();
            if (!currentItem.isEmpty()) {
                try {
                    CompoundTag itemNBT = currentItem.getTag();
                    CompoundTag newNBT = TagParser.parseTag(nbt);
                    itemNBT.merge(newNBT);
                    currentItem.setTag(itemNBT);
                    return "[NBTVIEWER] added nbt data";
                } catch (CommandSyntaxException e) {
                    return "[NBTVIEWER] error adding nbt data";
                }
            } else {
                return "[NBTVIEWER] no held item";
            }
        } else {
            return "[NBTVIEWER] you are not an player";
        }
    }
    @CommandExecutor(subCommand = "merge")
    private String mergeNbt() {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();
            if (!currentItem.isEmpty()) {
                try {
                    String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    CompoundTag nbt = TagParser.parseTag(clipboard);
                    CompoundTag itemNBT = currentItem.getTag();
                    itemNBT.merge(nbt);
                    currentItem.setTag(itemNBT);
                    return "[NBTVIEWER] merged nbt data";
                } catch (UnsupportedFlavorException | IOException | CommandSyntaxException e) {
                    return "[NBTVIEWER] error merging nbt data";
                }
            } else {
                return "[NBTVIEWER] no held item";
            }
        } else {
            return "[NBTVIEWER] you are not an player";
        }
    }

}
