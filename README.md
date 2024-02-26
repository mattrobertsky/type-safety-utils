# Type safety for simple types

**type-safety-utils** provides runtime type safety for simple types:

E.g. to have a validated UKPostcode type.

```scala
import com.mttrbrtsky.types.RegexValidatedString

type UKPostcode = String @@ UKPostcode.Tag

object UKPostcode extends RegexValidatedString(
  regex = """^[A-Z]{1,2}[0-9][A-Z0-9]? ?[0-9][A-Z]{2}$"""
)    
```

```scala
case class PostalAddress(
    addressLine1: String,
    addressLine2: String,
    postcode: UKPostcode,
    ...
)
```

Instantiating a UKPostcode that failed to match the given regex would result in an IllegalArgumentException.

N.b. see [Wikipedia]("https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom#Validation") for explanation of the regex.



### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

