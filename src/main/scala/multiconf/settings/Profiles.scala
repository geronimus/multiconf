package net.geronimus.multiconf.settings

import net.geronimus.multiconf.values.{
  Name,
  TextLine
}
import java.util.UUID
import scala.util.{ Failure, Success, Try }

case class ProfileListing(
  name : String,
  description : String,
  isDefault : Boolean,
  id: String
)


class Profiles:

  def default : Option[ ProfileListing ] = defaultProfile match
    case Some( profile ) => defaultProfile.map( makeListing )
    case None => None

  def forName( name : String ) : Option[ ProfileListing ] =
    nameIndex.get( name ).map( makeListing )

  def list : Seq[ ProfileListing ] = defaultProfile match
    case None => Vector.empty
    case Some( profile ) => Vector( makeListing( profile ) ) ++
      nameIndex.values
        .filter( p => p != profile )
        .map( makeListing )

  def register(
    name : Name,
    description : Option[ TextLine ] = None,
    id : Option[ Name ] = None
  ) : Try[ ProfileListing ] =
    if nameIndex.contains( name.text ) then
      Failure(
        IdentifierAlreadyRegisteredException( printNameRef( name.text ) )
      )
    
    else if ( id != None && idIndex.contains( id.get.text ) ) then
      Failure(
        IdentifierAlreadyRegisteredException( printIdRef( id.get.text ) )
      )
    
    else
      var newProfile : Option[ Profile ] = None
      id match
        case None => newProfile = Some( Profile( name ) )
        case Some( idValue ) => newProfile = Some( Profile( name, idValue ) )
      
      nameIndex += ( newProfile.get.name.text -> newProfile.get )
      idIndex += ( newProfile.get.id.text -> newProfile.get )
      descLookup += ( newProfile.get -> description.fold( "" )( _.text ) )

      if defaultProfile == None then defaultProfile = newProfile

      Success( makeListing( newProfile.get ) )

  def setDescription( id : String, description : TextLine ) : Try[ ProfileListing ] =
    idIndex.get( id ) match
      case None => Failure( IdentifierNotFoundException( printIdRef( id ) ) )
      case Some( profile ) =>
        descLookup += ( profile -> description.text )
        Success( makeListing( profile ) )

  private def makeListing( profile : Profile ) : ProfileListing =
    ProfileListing(
      name = profile.name.text,
      description = descLookup( profile ),
      isDefault = defaultProfile match
        case Some( p ) => p == profile
        case None => false,
      id = profile.id.text
    )

  private def printIdRef( id : String ) = s"id = ${ id }"
  private def printNameRef( name : String ) = s"name = ${ name }"

  private var defaultProfile : Option[ Profile ] = None
  private var descLookup : Map[ Profile, String ] = Map.empty
  private var idIndex : Map[ String, Profile ] = Map.empty
  private var nameIndex : Map[ String, Profile ] = Map.empty

  private class Profile(
    var name : Name,
    val id : Name = Name( UUID.randomUUID().toString )
  )

