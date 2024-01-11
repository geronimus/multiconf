package net.geronimus.multiconf.values

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers._

class TextLineSpec extends AnyFunSpec {

  describe( "TextLine:" ) {

    it( "Must not contain line ends." ) {

      assertThrows[ IllegalArgumentException ] {
        
        Set(
          "\u000a",
          "\u000b",
          "\u000c",
          "\u000d",
          "\u0085",
          "\u2028",
          "\u2029"
        ).foreach( lineEnd => {
          TextLine( s"Hello Muddah!${ lineEnd }Hello Fadda!" )
        })
      }

      noException shouldBe thrownBy {
        TextLine( "    Spaces!\tNot tabs!" )
      }
    }

    it( "Equal texts compare as equal." ) {

      val vals = (
        for test <- 1 to 2
        yield (
          test, TextLine( "I am the very model of a modern major general." )
        )
      ).toMap

      assert( vals( 1 ) == vals( 2 ) )
      assert(
        vals( 2 ) !=
          TextLine( "I've information vegetable, animal, and mineral." )
      )
    }
  }
}

