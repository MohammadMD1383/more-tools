# Minecraft Item Implementation Checklist

## 1. Registry / identity
- [ ] Register the item with a stable `Identifier`
- [ ] Put registration in the common/main source set
- [ ] Use the correct item class (`Item` or custom class when behavior requires it)
- [ ] Decide whether the item needs custom behavior/hooks

## 2. Basic item properties
- [ ] Stack size
- [ ] Durability
- [ ] Rarity
- [ ] Fire resistance
- [ ] Food properties (if food)
- [ ] Use duration / cooldown (if applicable)
- [ ] Any required data components

## 3. Attributes
- [ ] Attack damage
- [ ] Attack speed
- [ ] Attack knockback
- [ ] Armor value
- [ ] Armor toughness
- [ ] Knockback resistance
- [ ] Other custom attributes
- [ ] IMPORTANT: when adding an attribute, preserve the attributes
  already supplied by `.sword()`, `.pickaxe()`, armor properties, etc.
  Don't replace the whole ATTRIBUTE_MODIFIERS component accidentally.

## 4. Tool / weapon configuration
- [ ] Correct `ToolMaterial`
- [ ] Mining speed
- [ ] Mining tier / incorrect-block tag
- [ ] Durability
- [ ] Material attack-damage bonus
- [ ] Enchantability
- [ ] Repair ingredient tag
- [ ] Correct weapon/tool attributes

## 5. Armor configuration
- [ ] Armor material
- [ ] Armor value per slot
- [ ] Armor toughness
- [ ] Knockback resistance
- [ ] Durability
- [ ] Enchantability
- [ ] Repair ingredient tag
- [ ] Correct armor slot
- [ ] Armor texture(s)
- [ ] Trim compatibility, if desired

## 6. Enchantments
- [ ] Add item to appropriate vanilla enchantable tags
- [ ] Check which enchantments should work
- [ ] Check which enchantments should NOT work
- [ ] Test enchanting table
- [ ] Test anvil
- [ ] Test enchanted books

## 7. Tags
- [ ] Tool tags
- [ ] Weapon tags
- [ ] Armor tags
- [ ] Enchantable tags
- [ ] Repair-material tag
- [ ] Mining/incorrect-block tags
- [ ] Other vanilla compatibility tags
- [ ] Custom mod tags
- [ ] Use `addTag()` when the referenced tag is guaranteed available
- [ ] Use `addOptionalTag()` when the referenced tag may be absent
- [ ] Use `forceAddTag()` only when you intentionally want to bypass validation

## 8. Crafting / obtaining
- [ ] Crafting recipe
- [ ] Recipe uses correct ingredients
- [ ] Recipe unlock criteria, if appropriate
- [ ] Other acquisition method(s), if appropriate
- [ ] Smelting/blasting/etc. if applicable

## 9. Creative inventory
- [ ] Add to appropriate creative tab
- [ ] Correct ordering relative to vanilla/custom items

## 10. Client assets
- [ ] Texture PNG
- [ ] Item model
- [ ] Client item definition (`assets/<mod>/items/...json`)
- [ ] Correct `generated` vs `handheld` model
- [ ] Verify inventory rendering
- [ ] Verify first-person rendering
- [ ] Verify third-person rendering
- [ ] Armor model/texture if armor

## 11. Translation
- [ ] `en_us` translation
- [ ] Other languages, if supported
- [ ] Verify item name
- [ ] Verify attribute tooltip

## 12. Datagen
- [ ] Item models
- [ ] Client item definitions
- [ ] Recipes
- [ ] Tags
- [ ] Loot tables, if needed
- [ ] Translations
- [ ] Other data files
- [ ] Run datagen and inspect generated output

## 13. Loot / world integration
- [ ] Loot tables, if the item can appear in loot
- [ ] Mob drops, if applicable
- [ ] Structure/chest loot, if applicable
- [ ] Villager trades, if applicable
- [ ] Other world-generation integration

## 14. Gameplay testing
- [ ] `/give` works
- [ ] Correct name
- [ ] Correct texture
- [ ] Correct model
- [ ] Correct attributes
- [ ] Correct durability
- [ ] Correct damage
- [ ] Correct attack speed
- [ ] Correct knockback
- [ ] Correct mining behavior
- [ ] Correct enchantments
- [ ] Correct repair behavior
- [ ] Correct crafting
- [ ] Correct creative-tab placement
- [ ] Test with mobs
- [ ] Test with players
- [ ] Test survival
- [ ] Test creative
- [ ] Restart client and retest
- [ ] Test after regenerating data