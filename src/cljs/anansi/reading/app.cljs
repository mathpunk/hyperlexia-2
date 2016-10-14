(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]
            [anansi.reading.components :as c]
            [reagent-material-ui.core :refer [List ListItem]] ))

(defonce state
  (let [data db/data
        session {:unread (count (:pins db/data))}]
        (atom (merge db/data session))))

(defn welcome []
  [:div#welcome [:h2 "Good morning--"]])

(defn summary [data]
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source
        (str "viewing " (:user @state) "'s pins, retrieved " (:date @state))]
      ; [:div#source
      ;   (str "viewing " (.-user @state) "'s pins, retrieved " (.-date @state))]
    ])

(defn app []
  [:div#app
    [welcome]
    [summary]
  ])

(defn init []
  (reagent/render-component [app]
                            (.getElementById js/document "container")))
