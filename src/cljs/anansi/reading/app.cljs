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

(defn summary []
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source
        (str "viewing " (:user @state) "'s pins, retrieved " (:date @state))]
      [:div#progress (str (count (:pins @state)) " pins to review")]
    ])

(defn pins []
  [:div#pins {:style {:margin-top 20}}
    (map c/card (:pins @state))
  ])



(defn app []
  [:div#app
    [welcome]
    [summary]
    [pins]
  ])

(defn init []
  (reagent/render-component [app]
                            (.getElementById js/document "container")))
