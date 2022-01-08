package projetMowItNow

/*
* La classe Tondeuse permet d'instancier des objets tondeuse, contenant :
* - coordonnees : les coordonnées (x,y) actuelles
* - orientation : l'orientation actuelle ('N' / 'S' / 'E' / 'W')
* - maxCoordonnees : la taille de la carte
 */
class Tondeuse(var coordonnees:(Int, Int),
               var orientation:Char,
               val maxCoordonnees:(Int, Int)) {

  /*
  * Cette carte nous permet de savoir quelle est la nouvelle orientation après une consigne de virage à gauche/droite.
  * Par exemple, si nous sommes face au nord et que nous voulons tourner à gauche, nous prendrons la première valeur du tuple de clé 'N'
   */
  private val orientationMap = Map('N' -> ('W', 'E'), 'E' -> ('N', 'S'), 'S' -> ('E', 'W'), 'W' -> ('S', 'N'))

  /*
  * Modifie les coordonnées en avançant, uniquement si cela ne fait pas sortir la tondeuse de la carte.
   */
  private def goForward(): Unit ={
    this.coordonnees = this.orientation match {
      case 'N' if this.coordonnees._2 + 1 <= this.maxCoordonnees._2 =>  (this.coordonnees._1, this.coordonnees._2 + 1)
      case 'W' if this.coordonnees._1 - 1 >= 0 => (this.coordonnees._1 - 1, this.coordonnees._2)
      case 'S' if this.coordonnees._2 - 1 >= 0 => (this.coordonnees._1, this.coordonnees._2 - 1)
      case 'E' if this.coordonnees._1 + 1 <= this.maxCoordonnees._2 => (this.coordonnees._1 + 1, this.coordonnees._2)
      case _ => this.coordonnees
    }
  }

  /*
  * Appelle la bonne fonction en fonction de l'instruction en cours.
   */
  private def move(instruction:Char):Unit = {
    instruction match {
      case 'G' => this.orientation = this.orientationMap(this.orientation)._1
      case 'D' => this.orientation = this.orientationMap(this.orientation)._2
      case 'A' => this.goForward()
      case _ => throw new IllegalArgumentException()
    }
  }

  /*
* Format de chaîne caractère pour afficher le résultat final.
*/
  override def toString: String = {
    this.coordonnees._1 + " " + this.coordonnees._2 + " " + this.orientation
  }

  /*
  * Exécute séquentiellement toutes les instructions de la chaîne de caractère.
   */
  def execution(instructions:String): Unit ={
    for(instruction <- instructions){
      try{
        this.move(instruction)
      } catch {
        case _: IllegalArgumentException => throw new IllegalArgumentException("'" + instruction + "'" +
          " l'instruction dans le fichier d'entrée n'est pas prise en charge.")
      }
    }
  }
}
