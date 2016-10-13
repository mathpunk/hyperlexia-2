(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]))

(defn some-component []
  [:div
   [:h3 "I am a component!"]
   [:p.someclass
    "I represent " [:strong db/c]
    [:span {:style {:color "green"}} " elements"]]])

(defn calling-component []
  [:div "Parent component"
   [some-component]])

(defn init []
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
