package com.github.kelemen.brazier.weapons;

import com.github.kelemen.brazier.EntityId;
import java.util.Objects;
import org.jtrim.utils.ExceptionHelper;

public final class WeaponId implements EntityId {
    private final String name;

    public WeaponId(String name) {
        ExceptionHelper.checkNotNullArgument(name, "name");
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final WeaponId other = (WeaponId)obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
