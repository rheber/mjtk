(ns mjtk.core
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

; Tiles
(s/def :mjtk/rank (s/and int? #(>= % 1) #(<= % 9)))
(s/def :mjtk/suit #{:crc :dot :bam})
(s/def :mjtk/reddora boolean?)
(s/def :mjtk/number (s/keys :req [:mjtk/rank :mjtk/suit] :opt [:mjtk/reddora]))
(s/def :mjtk/wind #{:east :south :west :north})
(s/def :mjtk/dragon #{:red :white :green})
(s/def :mjtk/honor (s/or :wind :mjtk/wind :dragon :mjtk/dragon))
(s/def :mjtk/tile (s/or :number :mjtk/number :honor :mjtk/honor))

; Tilestrings
(s/def :mjtk/tilestring
  #(re-seq
     #"^(([0-9]+[mwcpdsb])|([1-7]+[zh]))*$"
     %))

(defn tilePairs<-tilestring [tilestring]
  "Convert a tilestring to a list of tiles."
  (def clumps (map first
                   (re-seq  #"([0-9]+[mwcpdsb])|([1-7]+[zh])" tilestring)))
  (def paired
    (apply concat
           (map (fn [clump]
                  (map #(list (last clump) %)
                       (butlast clump))) clumps)))
  paired
)

(defn -main
  "Main function."
  [& args]
  (if args 
    (let [tilestring (first args)]
      (if (s/valid? :mjtk/tilestring tilestring)
          (println (tilePairs<-tilestring tilestring))
          (println "Invalid tilestring.")
      )
    )
  )
)
