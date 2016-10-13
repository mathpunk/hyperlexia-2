(ns anansi.reading.db
  (:require [cognitect.transit :as transit]))

(slurp "resources/recent_pins.json")

(def pin-data (.fileRead fs "data/recent_pins.json"))

(def r (transit/reader :json))

(def c (count (r pin-data)))
