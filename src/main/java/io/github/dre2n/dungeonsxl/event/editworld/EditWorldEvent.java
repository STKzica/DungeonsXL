/*
 * Copyright (C) 2012-2016 Frank Baumann
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.dre2n.dungeonsxl.event.editworld;

import io.github.dre2n.dungeonsxl.dungeon.EditWorld;
import org.bukkit.event.Event;

/**
 * @author Daniel Saukel
 */
public abstract class EditWorldEvent extends Event {

    protected EditWorld editWorld;

    public EditWorldEvent(EditWorld editWorld) {
        this.editWorld = editWorld;
    }

    /**
     * @return the editWorld
     */
    public EditWorld getEditWorld() {
        return editWorld;
    }

    /**
     * @param editWorld
     * the editWorld to set
     */
    public void setEditWorld(EditWorld editWorld) {
        this.editWorld = editWorld;
    }

}
