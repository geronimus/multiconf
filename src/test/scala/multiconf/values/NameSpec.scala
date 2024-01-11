package net.geronimus.multiconf.values

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers._
import scala.collection.immutable.Range
import scala.util.Random

class NameSpec extends AnyFunSpec {

  describe( "Name:" ) {

    it( "Must have between 1 and 255 characters." ) {

      assertThrows[ IllegalArgumentException ] {
        Name( "" )
      }

      assertThrows[ IllegalArgumentException ] {
        Name(
          Range.inclusive( 1, 256 )
            .map( _ => Random.between( 33, 126 ) )
            .map( _.asInstanceOf[ Char ] )
            .mkString
        )
      }

      noException shouldBe thrownBy { Name( "Name" ) }
    }

    it( "Must not contain line ends or tabs." ) {
    
      Vector( "\r", "\n", "\t" ).foreach {
        lineEnd => {
          val illegalName = "Tea&for&Three".replaceAll( "&", lineEnd )
          assertThrows[ IllegalArgumentException ] { Name( illegalName ) }
        }
      }
    }

    it( "Identical values read as equal." ) {

      val homer = Name( "Dan Castellaneta" )
      val krusty = Name( "Dan Castellaneta" )

      assert( homer == krusty )
    }

    it( "Values surrounded by blank space get trimmed." ) {

      val mouse = Name( "Mouse" )
      val spaceMouse = Name(
        "\r\n\u000b\u000c\t \u00a0\u2000Mouse\u2001\u2002\u0085\u2028\u2029"
      )

      assert( mouse == spaceMouse )
    }

    it( "You can access the name text." ) {

      val nameText = "Kelsey Grammer School"
      val name = Name( nameText )
      
      assert( name.text == nameText )
    }

    describe( "isValid( text : String ) : Boolean" ) {

      it( "Tells you whether a given text is a valid name." ) {

        val illegalChars = Vector( 9, 10, 11, 12, 13, 133, 8232, 8233 )
          .map( _.toChar.toString )

        val invalidNames = Vector(
          // Too short:
          "",
          // Too long:
          Range.inclusive( 1, 256 )
            .map( _ => Random.between( 32, 126 ) )
            .map( _.asInstanceOf[ Char ] )
            .mkString
        ) ++
          // With invalid chars:
          illegalChars.map( c => s"Hello Muddah!${ c }Hello Fadda!" )

        invalidNames.foreach( invalid =>
          assert( !Name.isValid( invalid ) )
        )

        assert( Name.isValid( "My baloney has a first name!" ) )
      }
    }
  }
}

