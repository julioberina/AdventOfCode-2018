(defn rm-ind [s ind]
  (let [result (atom "")]
    (dotimes [i (count s)]
      (if (not= i ind) (swap! result str (get s i))))
    @result))

(let [words (clojure.string/split (slurp "day2input.txt") #"\n")
      twos (atom 0)
      threes (atom 0)
      seen (atom #{})]

  (doseq [word words]
    (let [letters (frequencies word)]
      (if (contains? (set (vals letters)) 2) (swap! twos inc))
      (if (contains? (set (vals letters)) 3) (swap! threes inc))))

  (println "Checksum:" (* @twos @threes))

  ;; Part 2
  (dotimes [i (count (first words))]
    (doseq [word words]
      (let [cut (rm-ind word i)]
        (if (contains? @seen cut)
          (println cut))
        (swap! seen conj cut)))
    (reset! seen #{})))
