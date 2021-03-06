package com.github.kelemen.brazier.abilities;

import com.github.kelemen.brazier.World;
import com.github.kelemen.brazier.events.UndoableUnregisterRef;
import com.github.kelemen.brazier.events.UndoableUnregisterRefBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jtrim.utils.ExceptionHelper;

public interface Buff<Target> {
    public UndoableUnregisterRef buff(World world, Target target, BuffArg arg);

    public default PermanentBuff<Target> toPermanent() {
        return (World world, Target target, BuffArg arg) -> {
            return buff(world, target, arg);
        };
    }

    public static <Target> Buff<Target> merge(
            Collection<? extends Buff<Target>> buffs) {
        List<Buff<Target>> buffsCopy = new ArrayList<>(buffs);
        ExceptionHelper.checkNotNullElements(buffsCopy, "buffs");

        int count = buffsCopy.size();
        if (count == 0) {
            return (world, actor, arg) -> UndoableUnregisterRef.UNREGISTERED_REF;
        }
        if (count == 1) {
            return buffsCopy.get(0);
        }

        return (World world, Target target, BuffArg arg) -> {
            UndoableUnregisterRefBuilder result = new UndoableUnregisterRefBuilder(buffsCopy.size());
            for (Buff<Target> buff: buffsCopy) {
                result.addRef(buff.buff(world, target, arg));
            }
            return result;
        };
    }
}
