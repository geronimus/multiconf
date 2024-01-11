package net.geronimus.multiconf.values

object Text:

  object LineEnd:
    val LineFeed = '\u000a'
    val LineTab = '\u000b'
    val FormFeed = '\u000c'
    val CarriageReturn = '\u000d'
    val NextLine = '\u0085'
    val LineSeparator = '\u2028'
    val ParagraphSeparator = '\u2029'

  val lineEndSet : Set[ Char ] = Set(
    LineEnd.LineFeed,
    LineEnd.LineTab,
    LineEnd.FormFeed,
    LineEnd.CarriageReturn,
    LineEnd.NextLine,
    LineEnd.LineSeparator,
    LineEnd.ParagraphSeparator
  )

  object Space:
    val Tab = '\u0009'
    val Space = '\u0020'
    val NoBreakSpace = '\u00a0'
    val OghamSpaceMark = '\u1680'
    val EnQuad = '\u2000'
    val EmQuad = '\u2001'
    val EnSpace = '\u2002'
    val EmSpace = '\u2003'
    val ThreePerEmSpace = '\u2004'
    val FourPerEmSpace = '\u2005'
    val SixPerEmSpace = '\u2006'
    val FigureSpace = '\u2007'
    val PunctuationSpace = '\u2008'
    val ThinSpace = '\u2009'
    val HairSpace = '\u200a'
    val NarrowNoBreakSpace = '\u202f'
    val MediumMathematicalSpace = '\u205f'
    val IdeographicSpace = '\u3000'
    val MongolianVowelSeparator = '\u180e'
    val ZeroWidthSpace = '\u200b'
    val ZeroWidthNonJoiner = '\u200c'
    val ZeroWidthJoiner = '\u200d'
    val WordJoiner = '\u2060'
    val ZeroWidthNonBreakingSpace = '\ufeff'

  val spaceSet : Set[ Char ] = Set(
    Space.Tab,
    Space.Space,
    Space.NoBreakSpace,
    Space.OghamSpaceMark,
    Space.EnQuad,
    Space.EmQuad,
    Space.EnSpace,
    Space.EmSpace,
    Space.ThreePerEmSpace,
    Space.FourPerEmSpace,
    Space.SixPerEmSpace,
    Space.FigureSpace,
    Space.PunctuationSpace,
    Space.ThinSpace,
    Space.HairSpace,
    Space.NarrowNoBreakSpace,
    Space.MediumMathematicalSpace,
    Space.IdeographicSpace,
    Space.MongolianVowelSeparator,
    Space.ZeroWidthSpace,
    Space.ZeroWidthNonJoiner,
    Space.ZeroWidthJoiner,
    Space.WordJoiner,
    Space.ZeroWidthNonBreakingSpace
  )

  val allSpaceCharSet = spaceSet union lineEndSet

  def closeShave( text : String ) =

    def isSpace( character : Char ) = allSpaceCharSet.contains( character )

    text.dropWhile( isSpace )
      .reverse
      .dropWhile( isSpace )
      .reverse
    
    
