package net.geronimus.multiconf.settings

import net.geronimus.multiconf.values.{ Name, TextLine }
import org.scalatest.funspec.AnyFunSpec
import scala.util.{ Failure, Success }

class ProfilesSpec extends AnyFunSpec:

  describe( "Profiles:" ) {

    describe(
      "register( name : Name, description : Option[ TextLine ] = None, " +
        "id : Option[ Name ] = None ) : Try[ ProfileListing ]"
    ) {
      it( "You cannot register the same name twice." ) {

        val profiles = Profiles()
        profiles.register( Name( "global" ) ) match
          case Failure( _ ) => fail()
          case _ => ()
        profiles.register( Name( "global" ) ) match
          case Failure( _ : IdentifierAlreadyRegisteredException ) => succeed
          case _ => fail()
      }

      it( "The first profile registered becomes the default." ) {

        val profiles = Profiles()
        assert( profiles.list == Seq.empty[ ProfileListing ] )
        assert( profiles.default == None )

        val loadNames = Vector( "global", "dev01" )
        loadNames
          .map( Name( _ ) )
          .foreach( profiles.register( _ ) )

        profiles.default match
          case Some( profile : ProfileListing ) =>
            assert( profile.name == "global" )
          case None => fail()

        val listing = profiles.list
        assert( listing.size == 2 )
        assert( listing( 0 ).name == "global" )
        assert( listing( 0 ).isDefault )
        assert( listing( 1 ).name == "dev01" )
        assert( !listing( 1 ).isDefault )
      }

      it(
        "If not provided, it assigns each registered profile a unique, " +
          "durable identifier."
      ) {

        val profiles = Profiles()
        val profileName = "default"
        val profile = profiles.register( Name( profileName ) ) match
          case Success( pl : ProfileListing ) => pl
          case Failure( _ ) => fail()

        assert( profile.id.isInstanceOf[ String ] )
        assert( profile.id.length == 36, s"id: ${ profile.id }" )
      }

      it(
        "But you can also provide an identifier at registration time. If it " +
          "is not already in use, it will be accepted."
      ) {
        val profiles = Profiles()
        val profileName = "default"
        val explicitId = "tweedle-dee"

        val listing = profiles.register(
          name = Name( profileName ),
          id = Some( Name( explicitId ) )
        )

        listing match
          case f : Failure[ ProfileListing ] => fail()
          case _ => ()

        assert( listing.get.id == explicitId )

        val secondProfile = "dev01"
        profiles.register(
          name = Name( secondProfile ),
          id = Some( Name( explicitId ) )
        ) match
          case Failure( e : IdentifierAlreadyRegisteredException ) => succeed
          case _ => fail()
      }

      it( "You can add a description when you register a profile." ) {

        val profiles = Profiles()
        val profileName = "global"
        val profileDesc = "The fallback profile, used when no more specific " +
          "setting is available."

        val profile = profiles.register(
          Name( profileName ),
          Some( TextLine( profileDesc ) )
        ) match
          case Success( pl : ProfileListing ) => pl
          case _ => fail()

        assert( profile.description == profileDesc )
      }
    }

    describe( "forName( name : String ) : Option[ ProfileListing ]" ) {
    
      it( "Can return a ProfileListing based on its name." ) {
      
        val pros = Profiles()
        val bros = Vector(
          ( "Harpo", "The silent partner." ),
          ( "Groucho", "The wise-ass." ),
          ( "Chico", "The brutta cafone." )
        )

        bros
          .map( b => ( Name( b._1 ), Some( TextLine( b._2 ) ) ) )
          .foreach( initTuple => pros.register( initTuple._1, initTuple._2 ) )

        pros.forName( "Zeppo" ) match
          case Some( _ ) => fail()
          case _ => ()

        pros.forName( bros( 1 )._1 ) match
          case Some( pl : ProfileListing ) =>
            assert( pl.name == bros( 1 )._1 )
            assert( pl.description == bros( 1 )._2 )
          case _ => fail()
      }
    }

    describe( 
      "setDescription( id : String, description : TextLine ) : Try[ Unit ]"
    ) {
      it( "Rejects unregistered ids." ) {

        val profiles = Profiles()
        val profileDesc = "The fallback profile, used when no more specific " +
          "setting is available."
        
        profiles.setDescription( "inextistent-id", TextLine( profileDesc ) ) match
          case Failure( _ : IdentifierNotFoundException ) => succeed
          case _ => fail()
      }

      it( "You can modify a description after it is initially set." ) {

        val profiles = Profiles()
        val profileName = "global"
        val profileDesc = "The fallback profile, used when no more specific " +
          "setting is available."
        val globalProfile = profiles.register( Name( profileName ) ) match
          case Success( pl : ProfileListing ) => pl
          case _ => fail()

        assert( globalProfile.description == "" )

        val result = profiles.setDescription(
          globalProfile.id,
          TextLine( profileDesc )
        ) match
          case Success( pl : ProfileListing ) => pl
          case _ => fail()

        assert( result.description == profileDesc )
      }
    }
  }

