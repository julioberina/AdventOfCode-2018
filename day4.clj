; (re-seq #"\w+" input)

(defn utslist [stamp]
  (map #(clojure.string/join "" %) (partition 2 stamp)))

(defn third [lst] (nth lst 2))
(defn fourth [lst] (nth lst 3))

(let [inputs (-> (slurp "day4input.txt")
              (clojure.string/split #"\n")
              (->> (map #(re-seq #"\w+" %)))
              (->> (map rest)))
      activity (atom (sorted-map))
      current-guard (atom nil)
      current-minute (atom nil)
      guard-sleep-total (atom {})
      guard-sleep-minutes (atom {})]

  (doseq [line inputs]
    (let [lk (clojure.string/join #"" (take 4 line))
          lv (drop 4 line)]
      (swap! activity assoc lk lv)))

  (doseq [[k v] @activity]
    (spit "sorted.txt" (str k " " (clojure.string/join #" " v) "\n") :append true)
    (case (first v)
      "Guard" (reset! current-guard (second v))
      "falls" (let [uts (utslist k)]
                (reset! current-minute (Integer/parseInt (last uts)))
                (if (nil? (@guard-sleep-minutes @current-guard))
                  (swap! guard-sleep-minutes assoc @current-guard [@current-minute])
                  (swap! guard-sleep-minutes assoc @current-guard
                         (conj (@guard-sleep-minutes @current-guard) @current-minute))))
      "wakes" (let [uts (utslist k) last-minute (dec (Integer/parseInt (last (utslist k))))]
                (if (nil? (@guard-sleep-total @current-guard))
                  (swap! guard-sleep-total assoc @current-guard (- last-minute @current-minute))
                  (swap! guard-sleep-total assoc @current-guard
                         (+ (- last-minute @current-minute) (@guard-sleep-total @current-guard)))))))

  (println "Guard sleep totals:")
  (println @guard-sleep-total)
  (println "Guard sleep minutes:")
  (println @guard-sleep-minutes))
