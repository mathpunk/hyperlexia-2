(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db] ))

(def state
  (let [data db/data
        view {:view {:posts {:active (nth (:posts data) 0)}}}]
    (atom (merge data view))))

; (defn card [{:keys [href tags], :as props}]
;   "Our first real component."
;   (do (. js/console log href)
;     ;; [:div {:style {:background-color "light blue"}} [:span.href {:style {:color "violet"}} href]]
;     [:div {:style {:background-color "blue" :height 120 :width 450}}] ))

; (defn card [{:keys [href description extended meta hash time shared toread tags] :as props}]
;   [:p "decsription"])

(defn card [c]
  [:p (str (js->clj c))])

(defn summary [data]
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source (str "viewing " (:user @state) "'s pins, retrieved " (:date @state) )]
      [:div#data
        [:div#keys "They have these keys: "
          (clojure.string/join  " " (:keys @state))]
        [:div#user "They have this user: " (:user @state)]
        [:div#posts "There are fewer than " (+ 1 (count (:posts @state))) " posts"
        [:div#post-data "The keys of a post are "
          (let [post ;(get-in @state [:view :posts :active])]
                 {:hi "there"}]
            [:p (keys post)])
            [:p "What we care about are firstly, "]
            [:code "href tags"]
            [:p "What we care about are secondly, "]
            [:code "href description extended tags"]
            [:article#components
              [:h2 "part 2: real components"]
              [card (first (:posts @state))]
            ] ]]]])

(defn welcome []
  [:div#welcome "Good morning--"])

(defn app []
  [:div#app
    [welcome]
    [summary]
  ])

(defn init []
  (reagent/render-component [app]
                            (.getElementById js/document "container")))
