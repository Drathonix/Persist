# 1.2.0 - The JSON Update
* Adds support for JSON and JSON5.
* Adds a migrator feature that automatically converts between supported file formats. This is enabled by default when using @PersistentPath.

# 1.2.1 - Avoid saving strings with multiple sets of quotes.

# 1.2.2 - Made @Range actually clamp number fields to the marked range. Also put safeguards in for integer overflow when parsing.

# 1.2.3 - Non-int and non-bool stringifiable objects will be quoted when saved.

# 1.2.4 - Flexibility and Future-proofing Improvements
* Added @AltName - Allows granting a savable element multiple names.
* Added @ReplaceKeys - Allows replacing keys on map read using a path structure.