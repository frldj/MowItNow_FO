package projetMowItNow

import scala.collection.mutable._
import scala.io.Source

object Demo {
  def main(args: Array[String]): Unit = {
    val file = Source.fromFile("./data/input.txt") // Chemin relatif où les données sont stockées
    // Lire le fichier et extraire les valeurs dans une liste
    val lines = file.getLines.toList
    file.close

    var listPositionInitiale = ListBuffer[Array[String]]()
    // Le premier élèment de la liste (ligne 0) correspond toujours aux coordonnées maximales de la carte
    val maxCoords = lines(0).split(" ")
    var instructions = ListBuffer[String]()
    for((line, i) <- lines.zipWithIndex){
      // Toutes les lignes impaires sont les positions initiales d'une tondeuse
      if(i > 0 && i%2 == 1){
        listPositionInitiale += line.split(" ")
      } else if(i > 0 && i%2 == 0){ // Toutes les lignes paires (après 0) sont des instructions d'exécution d'une tondeuse
        instructions += line
      }
    }

    // Pour chaque tondeuse
    println("La position finale pour chaque tondeuse est la suivante:")
    var iterateurTondeuse: Int = 0
    for((positionInitiale, instruction) <- listPositionInitiale.zip(instructions)){
      // Instanciation de la tondeuse
      iterateurTondeuse +=1
      val tondeuse = new Tondeuse(
        (positionInitiale(0).toInt, positionInitiale(1).toInt),
        positionInitiale(2).charAt(0),
        (maxCoords(0).toInt, maxCoords(1).toInt)
      )
      // Exécuter les instructions du fichier
      tondeuse.execution(instruction)
      //Affichage du résultat final
      println("Tondeuse"+iterateurTondeuse+":"+tondeuse.toString())
    }
  }
}
