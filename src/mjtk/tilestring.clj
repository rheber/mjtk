(ns mjtk.tilestring
  "Package for converting tilestrings into tiles."
  (:require
    [clojure.string :as str]
    [clojure.spec.alpha :as s]
  )
)

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

(defn tile<-tilePair [tilePair]
  "Convert a value of the form (p 0) into a value that meets the :mjtk/tile spec."
  (let [rank (second tilePair) suit (str (first tilePair))]
    (cond
      (str/includes? "zh" suit) (cond
        (= \1 rank) :east
        (= \2 rank) :south
        (= \3 rank) :west
        (= \4 rank) :north
        (= \5 rank) :red
        (= \6 rank) :white
        (= \7 rank) :green
        :else (throw (RuntimeException. (str "Invalid rank: " rank)))
      )
      (str/includes? "mwc" suit) (if (= \0 rank)
        {:mjtk/rank 5 :mjtk/suit :crc :mjtk/reddora true}
        {:mjtk/rank (Character/digit rank 10) :mjtk/suit :crc}
      )
      (str/includes? "pd" suit) (if (= \0 rank)
        {:mjtk/rank 5 :mjtk/suit :dot :mjtk/reddora true}
        {:mjtk/rank (Character/digit rank 10) :mjtk/suit :dot}
      )
      (str/includes? "sb" suit) (if (= \0 rank)
        {:mjtk/rank 5 :mjtk/suit :bam :mjtk/reddora true}
        {:mjtk/rank (Character/digit rank 10) :mjtk/suit :bam}
      )
      :else (throw (RuntimeException. (str "Invalid suit: " suit)))
    )
  )
)

