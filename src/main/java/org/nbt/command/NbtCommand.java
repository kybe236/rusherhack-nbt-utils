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

public class NbtCommand extends Command {

    public NbtCommand() {
        super("nbt", "View copy or paste NBT tags of held items");
    }

    @CommandExecutor(subCommand = "view")
    private String viewNbt() {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();

            if (!currentItem.isEmpty()) {
                CompoundTag itemNBT = currentItem.getTag();

                if (!itemNBT.isEmpty()) {
                    return "[NBTVIEWER]\n" + NbtUtils.prettyPrint(itemNBT);
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

    @CommandExecutor(subCommand = "view raw")
    private String viewRawNbt() {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();

            if (!currentItem.isEmpty()) {
                CompoundTag itemNBT = currentItem.getTag();

                if (!itemNBT.isEmpty()) {
                    return "[NBTVIEWER]\n" + itemNBT.toString();
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
    @CommandExecutor(subCommand = "copy pretty")
    private String copyPrettyNbt() {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();

            if (!currentItem.isEmpty()) {
                CompoundTag itemNBT = currentItem.getTag();

                if (!itemNBT.isEmpty()) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(NbtUtils.prettyPrint(itemNBT)), null);
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
    @CommandExecutor(subCommand = "copy")
    private String copyNbt() {
        if (mc.player != null) {
            ItemStack currentItem = mc.player.getMainHandItem();

            if (!currentItem.isEmpty()) {
                CompoundTag itemNBT = currentItem.getTag();
                if (!itemNBT.isEmpty()) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(itemNBT.toString()), null);
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

}
