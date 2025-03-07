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