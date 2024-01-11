package net.geronimus.multiconf.values

trait TextContainer( val text : String ):

  override def hashCode : Int = text.hashCode

  override def toString =
    s"${ this.getClass().getSimpleName() }( ${ text } )"

