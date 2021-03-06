/*
 * Copyright (C) 2014-2020 Daniel Saukel
 *
 * This library is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNULesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.erethon.dungeonsxl.api.dungeon;

import de.erethon.dungeonsxl.api.DungeonsAPI;
import java.util.Collection;
import org.bukkit.configuration.ConfigurationSection;

/**
 * A {@link GameRule} where the value is a {@link java.util.Collection}.
 *
 * @param <T> the type of the collection
 * @param <V> the type of the game rule value
 * @author Daniel Saukel
 */
public class CollectionGameRule<T, V extends Collection<T>> extends GameRule<V> {

    /**
     * @param key          the configuration key of the game rule
     * @param defaultValue the default value that is used when nothing is set
     */
    public CollectionGameRule(String key, V defaultValue) {
        super(null, key, defaultValue);
    }

    /**
     * @param key          the configuration key of the game rule
     * @param defaultValue the default value that is used when nothing is set
     * @param reader       a functional interface that loads the value from config
     */
    public CollectionGameRule(String key, V defaultValue, ConfigReader<V> reader) {
        super(null, key, defaultValue, reader);
    }

    /**
     * This implementation uses more expensive casting + catching the ClassCastException.
     * Developers should consider doing that themselves instead of wasting this cast.
     *
     * @param value the value
     * @return if the given value is an instance of {@link V}
     */
    @Override
    public boolean isValidValue(Object value) {
        try {
            V v = (V) value;
            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    @Override
    public V fromConfig(DungeonsAPI api, GameRuleContainer container, ConfigurationSection config) {
        Object value = config.get(getKey());
        if (reader != null) {
            V v = reader.read(api, value);
            container.setState(this, v);
            return v;
        }

        V v;
        try {
            v = (V) value;
        } catch (ClassCastException exception) {
            return null;
        }
        container.setState(this, v);
        return v;
    }

    @Override
    public void merge(GameRuleContainer overriding, GameRuleContainer subsidiary, GameRuleContainer writeTo) {
        V write = writeTo.getState(this);

        V subsidiaryState = subsidiary.getState(this);
        if (subsidiaryState != null) {
            if (write == null) {
                write = subsidiaryState;
            } else {
                write.addAll(subsidiaryState);
            }
        }

        V overridingState = overriding.getState(this);
        if (overridingState != null) {
            if (write == null) {
                write = overridingState;
            } else {
                write.addAll(overridingState);
            }
        }
        writeTo.setState(this, write);
    }

}
