(ns mjtk.core
  "Main file."
  (:require
    [clojure.pprint :as pp]
    [clojure.spec.alpha :as s]
  )
  (:use [mjtk.mentsu])
  (:use [mjtk.tilestring])
  (:gen-class)
)

(defn -main
  "Main function."
  [& args]
  (if args 
    (let [tilestring (first args)]
      (if (s/valid? :mjtk/tilestring tilestring)
          (let [tiles (map tile<-tilePair (tilePairs<-tilestring tilestring))]
            (pp/pprint tiles)
            (if (jantou? tiles)
              (println "This is a pair.")
              (println "This is not a pair.")
            )
          )
          (println "Invalid tilestring.")
      )
    )
  )
)
