# 1.2.0 - The JSON Update
* Adds support for JSON and JSON5.
* Adds a migrator feature that automatically converts between supported file formats. This is enabled by default when using @PersistentPath.

# 1.2.1 - Avoid saving strings with multiple sets of quotes.

# 1.2.2 - Made @Range actually clamp number fields to the marked range. Also put safeguards in for integer overflow when parsing.

# 1.2.3 - Non-int and non-bool stringifiable objects will be quoted when saved.

# 1.2.4 - Flexibility and Future-proofing Improvements
* Added @AltName - Allows granting a savable element multiple names.
* Added @ReplaceKeys - Allows replacing keys on map read using a path structure.
# 1.3.0 - Finally learned how to develop for multiple versions of java at the same time :face_palm: (It's been like, 8 years at this point :/)
Also there's some javadocs now. I'll be slowwwwwwly improving them don't quote me.
Also readme.md is better now.
# 1.3.1 - Added @Required, any field marked with this is required to be present when unmappifying.

# 1.3.2 - Fixed a performance bug with mappify on Fields with @Altname where serialization would be executed an additional time per name. Had no effect on output, just performance problems.
Added a test for @AltName as well.
# 1.3.3 - Publicly expose ReflectionHelper.forEach()

# 1.3.4 - Moved the ClassData map from Context to ClassData.

# 1.3.5 - Swapped to LinkedHashSets and Maps for FieldData order, this will only change the order of iteration and will ensure field order is kept in files.

# 1.4.0 - Fixed a poor design decision in @Save's raw() method

This update may drastically impact some programs as it involves the immediate deprecation of @Save.raw() which was a flawed design choice.

This marks the official introduction of "Objectified" and "Reference/Value" terminology.
Before 1.4.0 all Objects containing @Save fields were stored in Objectified format by default unless raw() was true. This is still the case barring a few exceptions.

Enums and Classes are now stored in Reference/Value format. They will be Objectified only if they contain @Save fields and their storing fields are marked with @Objectified.

# 1.4.1 - Fix a bug with Array storage where Boxed Primitive arrays are not able to be unmapped.

# 1.4.2 - Add generated initializers. @Save.Constructor can be used to mark a constructor to be used as an initializer and provide parameter names in metadata.
Initializers will be called when unmappifying objects instead of calling Field set operations.

# 1.4.5 - Now uploading directly to a public maven separate from github packages

# 1.4.6 - Fix a fatal bug with Transform keys due to missing array index.

# 1.4.7 - Added @Priority and @Ordering both of which allow further control over field saving order.
Also fixed a bug where classes containing required static and non-static fields would not be able to be deserialized properly.
WrappedObjectMap now extends LinkedHashMap so class level ordering should be maintained now.