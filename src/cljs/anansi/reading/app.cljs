(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]))

(defn some-component []
  [:div
   [:h3 "Posts from " (:date db/data)]
   [:p.someclass
    "I represent " [:strong (db/view)]
    [:span {:style {:color "green"}} " elements"]]])

(defn calling-component []
  [:div "Good morning--"
   [some-component]])

(defn summary-component [data]
  [:div#summary
  [:div#today "today's date is 2016-10-13"]
  [:div#recent "these posts are from " data]
  ])

(defn init []
  (reagent/render-component [summary-component db/data]
                            (.getElementById js/document "container")))
