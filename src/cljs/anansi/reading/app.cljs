(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]



          ))

(declare card)

(def state
  (let [data db/data
        view {:view {:posts {:active (nth (:posts data) 0)}}}]
    (atom (merge data view))))

(defn welcome []
  [:div#welcome [:h2 "Good morning--"]])

;; Welcome and summary
(defn summary [data]
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source (str "viewing " (:user @state) "'s pins, retrieved " (:date @state) )]])

(defn pins []
  [:div#pins
    [:section#rationale
      [:h2 "pins"]
      [:p "We care about firstly, "]
      [:ul [:li "href"] [:li "tags"]]
      [:p "Later we will care about"]
      [:ul [:li "href"] [:li "tags"]
           [:li "description"] [:li "extended"]]
    ]
    [:section#types
      [:h3 "Types of pins; material-ui"]
      [:p "I'd like to render different sorts of pins slightly differently. The first thing I think of for this is a multimethod. I've never used one before; I'm not certain it's the right pattern. But, since I've got a very simple sort of type system going on here, and core.match seems a bit much."]
      [:p "The other thing I want to do is bring in existing reactive component libraries and work with them, to gain facility with reusing components and to minimize the css wrangling i do until i know what I want to override."]
      [:p "Let's do the latter, first. My multimethod idea can then be tested with a working and nice-looking component, and I can see if it's a good way to define a component once and then vary it by dispatching on ad hoc types."]
    ]
  ])


; (defn card [{:keys [href tags], :as props}]
;   "Our first real component."
;   (do (. js/console log href)
;     ;; [:div {:style {:background-color "light blue"}} [:span.href {:style {:color "violet"}} href]]
;     [:div {:style {:background-color "blue" :height 120 :width 450}}] ))


(defn card [c]
  (let [card-data (js->clj c)]
  [:div.card
    [:p (card-data "description") (card-data "href")]
  ]))

(defn app []
  [:div#app
    [welcome]
    [summary]
    [pins]
  ])

(defn init []
  (reagent/render-component [app]
                            (.getElementById js/document "container")))
