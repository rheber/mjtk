(ns mjtk.mentsu
  "Package for checking whether a list of tiles is a mentsu."
  (:use [mjtk.tilestring])
)

(defn jantou? [tiles]
  "Whether a list of :mjtk/tile is a pair."
  (and
    (= 2 (count tiles))
    (= (get (get tiles 0) :mjtk/rank) (get (get tiles 1) :mjtk/rank))
    (= (get (get tiles 0) :mjtk/suit) (get (get tiles 1) :mjtk/suit))
  )
)

(defn koutsu? [tiles]
  "Whether a list of :mjtk/tile is a three-of-a-kind."
  (and
    (= 3 (count tiles))
    (= (get (get tiles 0) :mjtk/rank) (get (get tiles 1) :mjtk/rank))
    (= (get (get tiles 0) :mjtk/suit) (get (get tiles 1) :mjtk/suit))
    (= (get (get tiles 0) :mjtk/rank) (get (get tiles 2) :mjtk/rank))
    (= (get (get tiles 0) :mjtk/suit) (get (get tiles 2) :mjtk/suit))
  )
)
