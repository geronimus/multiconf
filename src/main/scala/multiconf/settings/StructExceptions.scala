package net.geronimus.multiconf.settings

case class IdentifierAlreadyRegisteredException( identifier : String )
  extends RuntimeException( identifier )

case class IdentifierNotFoundException( identifier : String )
  extends RuntimeException( identifier )

