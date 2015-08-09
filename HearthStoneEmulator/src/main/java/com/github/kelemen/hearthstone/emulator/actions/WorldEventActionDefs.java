package com.github.kelemen.hearthstone.emulator.actions;

import com.github.kelemen.hearthstone.emulator.ArmorGainedEvent;
import com.github.kelemen.hearthstone.emulator.CardPlayEvent;
import com.github.kelemen.hearthstone.emulator.CardPlayedEvent;
import com.github.kelemen.hearthstone.emulator.CompletableWorldEventBasedActionDef;
import com.github.kelemen.hearthstone.emulator.DamageEvent;
import com.github.kelemen.hearthstone.emulator.DamageRequest;
import com.github.kelemen.hearthstone.emulator.Player;
import com.github.kelemen.hearthstone.emulator.PlayerProperty;
import com.github.kelemen.hearthstone.emulator.Secret;
import com.github.kelemen.hearthstone.emulator.WorldEvents;
import com.github.kelemen.hearthstone.emulator.abilities.ActivatableAbility;
import com.github.kelemen.hearthstone.emulator.cards.Card;
import com.github.kelemen.hearthstone.emulator.minions.Minion;
import com.github.kelemen.hearthstone.emulator.weapons.Weapon;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.jtrim.collections.CollectionsEx;
import org.jtrim.utils.ExceptionHelper;

public final class WorldEventActionDefs<Self extends PlayerProperty> implements ActivatableAbility<Self> {
    public static final class Builder<Self extends PlayerProperty> {
        private final List<WorldEventBasedActionDef<Self, Card>> onDrawCardActionDefs;
        private final List<WorldEventBasedActionDef<Self, CardPlayEvent>> onStartPlayingCardActionDefs;
        private final List<WorldEventBasedActionDef<Self, CardPlayedEvent>> onDonePlayingCardActionDefs;

        private final List<CompletableWorldEventBasedActionDef<Self, Minion>> onSummoningActionDefs;

        private final List<WorldEventBasedActionDef<Self, DamageRequest>> onPrepareDamageListeners;
        private final List<WorldEventBasedActionDef<Self, DamageEvent>> onHeroDamagedActionDefs;
        private final List<WorldEventBasedActionDef<Self, DamageEvent>> onMinionDamagedActionDefs;
        private final List<WorldEventBasedActionDef<Self, Minion>> onMinionKilledActionDefs;
        private final List<WorldEventBasedActionDef<Self, Weapon>> onWeaponDestroyedActionDefs;

        private final List<WorldEventBasedActionDef<Self, ArmorGainedEvent>> onArmorGainedActionDefs;
        private final List<WorldEventBasedActionDef<Self, DamageEvent>> onHeroHealedActionDefs;
        private final List<WorldEventBasedActionDef<Self, DamageEvent>> onMinionHealedActionDefs;

        private final List<WorldEventBasedActionDef<Self, Player>> onTurnStartsActionDefs;
        private final List<WorldEventBasedActionDef<Self, Player>> onTurnEndsActionDefs;

        private final List<WorldEventBasedActionDef<Self, AttackRequest>> onAttackActionDefs;
        private final List<WorldEventBasedActionDef<Self, Secret>> onSecretRevealedActionDefs;

        public Builder() {
            this.onDrawCardActionDefs = new LinkedList<>();
            this.onStartPlayingCardActionDefs = new LinkedList<>();
            this.onDonePlayingCardActionDefs = new LinkedList<>();
            this.onSummoningActionDefs = new LinkedList<>();
            this.onPrepareDamageListeners = new LinkedList<>();
            this.onHeroDamagedActionDefs = new LinkedList<>();
            this.onMinionDamagedActionDefs = new LinkedList<>();
            this.onMinionKilledActionDefs = new LinkedList<>();
            this.onWeaponDestroyedActionDefs = new LinkedList<>();
            this.onArmorGainedActionDefs = new LinkedList<>();
            this.onHeroHealedActionDefs = new LinkedList<>();
            this.onMinionHealedActionDefs = new LinkedList<>();
            this.onAttackActionDefs = new LinkedList<>();
            this.onTurnStartsActionDefs = new LinkedList<>();
            this.onTurnEndsActionDefs = new LinkedList<>();
            this.onSecretRevealedActionDefs = new LinkedList<>();
        }

        public void addOnDrawCardActionDef(WorldEventBasedActionDef<Self, Card> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onDrawCardActionDefs.add(def);
        }

        public void addOnStartPlayingCardActionDef(WorldEventBasedActionDef<Self, CardPlayEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onStartPlayingCardActionDefs.add(def);
        }

        public void addOnDonePlayingCardActionDef(WorldEventBasedActionDef<Self, CardPlayedEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onDonePlayingCardActionDefs.add(def);
        }

        public void addOnSummoningActionDef(CompletableWorldEventBasedActionDef<Self, Minion> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onSummoningActionDefs.add(def);
        }

        public void addOnPrepareDamageActionDef(WorldEventBasedActionDef<Self, DamageRequest> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onPrepareDamageListeners.add(def);
        }

        public void addOnHeroDamagedActionDef(WorldEventBasedActionDef<Self, DamageEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onHeroDamagedActionDefs.add(def);
        }

        public void addOnMinionDamagedActionDef(WorldEventBasedActionDef<Self, DamageEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onMinionDamagedActionDefs.add(def);
        }

        public void addOnMinionKilledActionDef(WorldEventBasedActionDef<Self, Minion> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onMinionKilledActionDefs.add(def);
        }

        public void addOnWeaponDestroyedActionDef(WorldEventBasedActionDef<Self, Weapon> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onWeaponDestroyedActionDefs.add(def);
        }

        public void addOnArmorGainedActionDef(WorldEventBasedActionDef<Self, ArmorGainedEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onArmorGainedActionDefs.add(def);
        }

        public void addOnHeroHealedActionDef(WorldEventBasedActionDef<Self, DamageEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onHeroHealedActionDefs.add(def);
        }

        public void addOnMinionHealedActionDef(WorldEventBasedActionDef<Self, DamageEvent> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onMinionHealedActionDefs.add(def);
        }

        public void addOnAttackActionDef(WorldEventBasedActionDef<Self, AttackRequest> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onAttackActionDefs.add(def);
        }

        public void addOnTurnStartsActionDef(WorldEventBasedActionDef<Self, Player> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onTurnStartsActionDefs.add(def);
        }

        public void addOnTurnEndsActionDef(WorldEventBasedActionDef<Self, Player> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onTurnEndsActionDefs.add(def);
        }

        public void addOnSecretRevealedActionDefs(WorldEventBasedActionDef<Self, Secret> def) {
            ExceptionHelper.checkNotNullArgument(def, "def");
            onSecretRevealedActionDefs.add(def);
        }


        public WorldEventActionDefs<Self> create() {
            return new WorldEventActionDefs<>(this);
        }
    }

    private final List<WorldEventBasedActionDef<Self, Card>> onDrawCardActionDefs;
    private final List<WorldEventBasedActionDef<Self, CardPlayEvent>> onStartPlayingCardActionDefs;
    private final List<WorldEventBasedActionDef<Self, CardPlayedEvent>> onDonePlayingCardActionDefs;

    private final List<CompletableWorldEventBasedActionDef<Self, Minion>> onSummoningActionDefs;

    private final List<WorldEventBasedActionDef<Self, DamageRequest>> onPrepareDamageListeners;
    private final List<WorldEventBasedActionDef<Self, DamageEvent>> onHeroDamagedActionDefs;
    private final List<WorldEventBasedActionDef<Self, DamageEvent>> onMinionDamagedActionDefs;
    private final List<WorldEventBasedActionDef<Self, Minion>> onMinionKilledActionDefs;
    private final List<WorldEventBasedActionDef<Self, Weapon>> onWeaponDestroyedActionDefs;

    private final List<WorldEventBasedActionDef<Self, ArmorGainedEvent>> onArmorGainedActionDefs;
    private final List<WorldEventBasedActionDef<Self, DamageEvent>> onHeroHealedActionDefs;
    private final List<WorldEventBasedActionDef<Self, DamageEvent>> onMinionHealedActionDefs;

    private final List<WorldEventBasedActionDef<Self, Player>> onTurnStartsActionDefs;
    private final List<WorldEventBasedActionDef<Self, Player>> onTurnEndsActionDefs;

    private final List<WorldEventBasedActionDef<Self, AttackRequest>> onAttackActionDefs;
    private final List<WorldEventBasedActionDef<Self, Secret>> onSecretRevealedActionDefs;

    private final boolean hasAnyActionDef;

    private WorldEventActionDefs(Builder<Self> builder) {
        BoolRef hasListeners = new BoolRef(false);

        this.onDrawCardActionDefs = importListeners(builder.onDrawCardActionDefs, hasListeners);
        this.onStartPlayingCardActionDefs = importListeners(builder.onStartPlayingCardActionDefs, hasListeners);
        this.onDonePlayingCardActionDefs = importListeners(builder.onDonePlayingCardActionDefs, hasListeners);
        this.onSummoningActionDefs = importListeners(builder.onSummoningActionDefs, hasListeners);
        this.onPrepareDamageListeners = importListeners(builder.onPrepareDamageListeners, hasListeners);
        this.onHeroDamagedActionDefs = importListeners(builder.onHeroDamagedActionDefs, hasListeners);
        this.onMinionDamagedActionDefs = importListeners(builder.onMinionDamagedActionDefs, hasListeners);
        this.onMinionKilledActionDefs = importListeners(builder.onMinionKilledActionDefs, hasListeners);
        this.onWeaponDestroyedActionDefs = importListeners(builder.onWeaponDestroyedActionDefs, hasListeners);
        this.onArmorGainedActionDefs = importListeners(builder.onArmorGainedActionDefs, hasListeners);
        this.onHeroHealedActionDefs = importListeners(builder.onHeroHealedActionDefs, hasListeners);
        this.onMinionHealedActionDefs = importListeners(builder.onMinionHealedActionDefs, hasListeners);
        this.onTurnStartsActionDefs = importListeners(builder.onTurnStartsActionDefs, hasListeners);
        this.onTurnEndsActionDefs = importListeners(builder.onTurnEndsActionDefs, hasListeners);
        this.onAttackActionDefs = importListeners(builder.onAttackActionDefs, hasListeners);
        this.onSecretRevealedActionDefs = importListeners(builder.onSecretRevealedActionDefs, hasListeners);

        this.hasAnyActionDef = hasListeners.value;
    }

    private <E> List<E> importListeners(Collection<? extends E> c, BoolRef hasListeners) {
        List<E> result = CollectionsEx.readOnlyCopy(c);
        if (!result.isEmpty()) {
            hasListeners.value = true;
        }
        return result;
    }

    public UndoableUnregisterRef registerOnDrawCardAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onDrawCardActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnStartPlayingCardAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onStartPlayingCardActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnDonePlayingCardAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onDonePlayingCardActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnSummoningAction(WorldEvents worldEvents, Self self) {
        return CompletableWorldEventBasedActionDef.registerAll(onSummoningActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnPrepareDamageAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onPrepareDamageListeners, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnHeroDamagedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onHeroDamagedActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnMinionDamagedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onMinionDamagedActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnMinionKilledAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onMinionKilledActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnWeaponDestroyedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onWeaponDestroyedActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnArmorGainedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onArmorGainedActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnHeroHealedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onHeroHealedActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnMinionHealedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onMinionHealedActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnTurnStartsAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onTurnStartsActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnTurnEndsAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onTurnEndsActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnAttackAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onAttackActionDefs, worldEvents, self);
    }

    public UndoableUnregisterRef registerOnSecretRevealedAction(WorldEvents worldEvents, Self self) {
        return WorldEventBasedActionDef.registerAll(onSecretRevealedActionDefs, worldEvents, self);
    }

    @Override
    public UndoableUnregisterRef activate(Self self) {
        if (!hasAnyActionDef) {
            return UndoableUnregisterRef.UNREGISTERED_REF;
        }

        WorldEvents worldEvents = self.getWorld().getEvents();

        UndoableUnregisterRefBuilder result = new UndoableUnregisterRefBuilder();
        result.addRef(registerOnDrawCardAction(worldEvents, self));
        result.addRef(registerOnStartPlayingCardAction(worldEvents, self));
        result.addRef(registerOnDonePlayingCardAction(worldEvents, self));
        result.addRef(registerOnSummoningAction(worldEvents, self));
        result.addRef(registerOnPrepareDamageAction(worldEvents, self));
        result.addRef(registerOnHeroDamagedAction(worldEvents, self));
        result.addRef(registerOnMinionDamagedAction(worldEvents, self));
        result.addRef(registerOnMinionKilledAction(worldEvents, self));
        result.addRef(registerOnWeaponDestroyedAction(worldEvents, self));
        result.addRef(registerOnArmorGainedAction(worldEvents, self));
        result.addRef(registerOnHeroHealedAction(worldEvents, self));
        result.addRef(registerOnMinionHealedAction(worldEvents, self));
        result.addRef(registerOnMinionHealedAction(worldEvents, self));
        result.addRef(registerOnTurnStartsAction(worldEvents, self));
        result.addRef(registerOnTurnEndsAction(worldEvents, self));
        result.addRef(registerOnAttackAction(worldEvents, self));
        result.addRef(registerOnSecretRevealedAction(worldEvents, self));
        return result;
    }

    public boolean hasAnyActionDef() {
        return hasAnyActionDef;
    }

    private static final class BoolRef {
        private boolean value;

        public BoolRef(boolean value) {
            this.value = value;
        }
    }
}
