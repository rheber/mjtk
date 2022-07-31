(ns mjtk.core
  "Main file."
  (:require
    [clojure.pprint :as pp]
    [clojure.spec.alpha :as s]
  )
  (:use [mjtk.tilestring])
  (:gen-class)
)

(defn -main
  "Main function."
  [& args]
  (if args 
    (let [tilestring (first args)]
      (if (s/valid? :mjtk/tilestring tilestring)
          (pp/pprint (map tile<-tilePair (tilePairs<-tilestring tilestring)))
          (println "Invalid tilestring.")
      )
    )
  )
)
