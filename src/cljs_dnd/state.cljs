
(ns cljs-dnd.state
  (:require [reagent.core :as r]))

(def state (r/atom {:drag-index nil
                    :hover-index nil
                    ;; coords
                    :y nil
                    :x nil
                    ;; react-dnd
                    :is-dragging nil
                    :item-type nil
                    :init-client-offset nil
                    :client-offset nil
                    :init-src-client-offset nil
                    :src-client-offset nil
                    :diff-from-initial-offset nil}))

(defn add-source [id])
(defn remove-source [id])

(defn add-target [id])
(defn remove-target [id])

(defn manager [store monitor])

(defn test-backend [manager])
