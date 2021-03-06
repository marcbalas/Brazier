package com.github.kelemen.brazier.actions;

import com.github.kelemen.brazier.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jtrim.utils.ExceptionHelper;

public interface TargetlessAction<Actor> extends WorldObjectAction<Actor> {
    public static final TargetlessAction<Object> DO_NOTHING = (world, actor) -> UndoAction.DO_NOTHING;

    public default TargetedAction<Actor, Object> toTargetedAction() {
        return (World world, Actor actor, Object target) -> alterWorld(world, actor);
    }

    public static <Actor> TargetlessAction<Actor> merge(
            Collection<? extends TargetlessAction<Actor>> actions) {
        List<TargetlessAction<Actor>> actionsCopy = new ArrayList<>(actions);
        ExceptionHelper.checkNotNullElements(actionsCopy, "actions");

        int count = actionsCopy.size();
        if (count == 0) {
            return (world, actor) -> UndoAction.DO_NOTHING;
        }
        if (count == 1) {
            return actionsCopy.get(0);
        }

        return (World world, Actor actor) -> {
            UndoBuilder result = new UndoBuilder(actionsCopy.size());
            for (TargetlessAction<Actor> action: actionsCopy) {
                result.addUndo(action.alterWorld(world, actor));
            }
            return result;
        };
    }
}
