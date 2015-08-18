package com.github.kelemen.hearthstone.emulator.actions;

import com.github.kelemen.hearthstone.emulator.BoardLocationRef;
import com.github.kelemen.hearthstone.emulator.BoardSide;
import com.github.kelemen.hearthstone.emulator.Hero;
import com.github.kelemen.hearthstone.emulator.Keyword;
import com.github.kelemen.hearthstone.emulator.Player;
import com.github.kelemen.hearthstone.emulator.SummonLocationRef;
import com.github.kelemen.hearthstone.emulator.TargetableCharacter;
import com.github.kelemen.hearthstone.emulator.World;
import com.github.kelemen.hearthstone.emulator.cards.CardId;
import com.github.kelemen.hearthstone.emulator.minions.Minion;
import com.github.kelemen.hearthstone.emulator.minions.MinionBody;
import com.github.kelemen.hearthstone.emulator.parsing.NamedArg;
import java.util.Arrays;
import org.jtrim.utils.ExceptionHelper;

public final class BattleCries {
    private static Minion tryGetLeft(Minion minion) {
        SummonLocationRef locationRef = minion.getLocationRef();
        BoardLocationRef left = locationRef.tryGetLeft();
        return left != null ? left.getMinion() : null;
    }

    private static Minion tryGetRight(Minion minion) {
        SummonLocationRef locationRef = minion.getLocationRef();
        BoardLocationRef right = locationRef.tryGetRight();
        return right != null ? right.getMinion() : null;
    }

    public static final BattleCryTargetedAction CONSUME_NEIGHBOURS = (World world, BattleCryArg arg) -> {
        Minion minion = arg.getSource();
        int attackBuff = 0;
        int hpBuff = 0;

        UndoBuilder result = new UndoBuilder(4);

        Minion left = tryGetLeft(minion);
        if (left != null) {
            attackBuff += left.getAttackTool().getAttack();
            hpBuff += left.getBody().getCurrentHp();
            result.addUndo(ActorlessTargetedActions.KILL_TARGET.alterWorld(world, left));
        }

        Minion right = tryGetRight(minion);
        if (right != null) {
            attackBuff += right.getAttackTool().getAttack();
            hpBuff += right.getBody().getCurrentHp();
            result.addUndo(ActorlessTargetedActions.KILL_TARGET.alterWorld(world, right));
        }

        if (hpBuff != 0 && attackBuff != 0) {
            result.addUndo(minion.addAttackBuff(attackBuff));
            result.addUndo(minion.getBody().getHp().buffHp(hpBuff));
        }

        return result;
    };

    public static final BattleCryTargetedAction EXORCIST_BUFF = (World world, BattleCryArg arg) -> {
        Minion minion = arg.getSource();
        BoardSide opponentBoard = minion.getOwner().getOpponent().getBoard();
        int buff = opponentBoard.countMinions((opponentMinion) -> opponentMinion.getProperties().isDeathRattle());

        UndoAction attackBuffUndo = minion.addAttackBuff(buff);
        UndoAction hpBuffUndo = minion.getBody().getHp().buffHp(buff);
        return () -> {
            hpBuffUndo.undo();
            attackBuffUndo.undo();
        };
    };

    public static final BattleCryTargetedAction COPY_SELF = (world, arg) -> {
        return ActionUtils.doOnEndOfTurn(world, () -> {
            Minion self = arg.getSource();
            Player owner = self.getOwner();
            if (owner.getBoard().isFull()) {
                return UndoAction.DO_NOTHING;
            }

            Minion copy = new Minion(self.getOwner(), self.getBaseDescr());

            UndoAction copyUndo = copy.copyOther(self);
            UndoAction summonUndo = self.getLocationRef().summonRight(copy);
            return () -> {
                summonUndo.undo();
                copyUndo.undo();
            };
        });
    };

    public static BattleCryTargetedAction combined(
            @NamedArg("actions") BattleCryTargetedAction[] actions) {
        return BattleCryTargetedAction.merge(Arrays.asList(actions));
    }

    public static BattleCryTargetedAction doMultipleTimes(
            @NamedArg("actionCount") int actionCount,
            @NamedArg("action") BattleCryTargetedAction action) {
        ExceptionHelper.checkNotNullArgument(action, "action");
        if (actionCount == 1) {
            return action;
        }

        return (World world, BattleCryArg arg) -> {
            UndoBuilder result = new UndoBuilder(actionCount);
            for (int i = 0; i < actionCount; i++) {
                result.addUndo(action.alterWorld(world, arg));
            }
            return result;
        };
    }

    public static BattleCryTargetedAction replaceHero(
            @NamedArg("heroClass") Keyword heroClass,
            @NamedArg("heroPower") CardId heroPower) {

        return (World world, BattleCryArg arg) -> {
            Minion minion = arg.getSource();
            Player player = minion.getOwner();
            UndoAction removeUndo = minion.getLocationRef().removeFromBoard();

            MinionBody body = minion.getBody();

            Hero hero = new Hero(player, body.getHp(), 0, heroClass, minion.getKeywords());
            hero.setCurrentHp(body.getCurrentHp());
            hero.setHeroPower(world.getDb().getHeroPowerDb().getById(heroPower));

            UndoAction setHeroUndo = player.setHero(hero);
            return () -> {
                setHeroUndo.undo();
                removeUndo.undo();
            };
        };
    }

    public static BattleCryTargetedAction copyOther() {
        return (World world, BattleCryArg arg) -> {
            TargetableCharacter target = arg.getTarget().getTarget();
            if (target instanceof Minion) {
                return arg.getSource().copyOther((Minion)target);
            }
            else {
                return UndoAction.DO_NOTHING;
            }
        };
    }

    public static BattleCryTargetedAction dealDamageToTarget(@NamedArg("damage") int damage) {
        return (world, arg) -> {
            TargetableCharacter character = arg.getTarget().getTarget();

            return character != null
                    ? ActionUtils.damageCharacter(arg.getSource(), damage, character)
                    : UndoAction.DO_NOTHING;
        };
    }

    private BattleCries() {
        throw new AssertionError();
    }
}
