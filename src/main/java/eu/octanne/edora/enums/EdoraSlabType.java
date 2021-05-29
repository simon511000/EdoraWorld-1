package eu.octanne.edora.enums;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum EdoraSlabType implements StringIdentifiable {
   TOP("top"),
   BOTTOM("bottom"),
   DOUBLE("double"),
   WEST("west"),
   EAST("est"),
   NORTH("north"),
   SOUTH("south");

   public static final EnumProperty<EdoraSlabType> SLAB_TYPE = EnumProperty.of("type", EdoraSlabType.class);

   private final String name;

   private EdoraSlabType(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }

   public String asString() {
      return this.name;
   }

}
