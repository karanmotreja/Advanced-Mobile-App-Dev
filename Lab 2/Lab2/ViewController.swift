//
//  ViewController.swift
//  Lab2
//
//  Created by Karan Motreja on 2/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {

    var continentList = [String]()
    var continentsData = ContinentsDataModelController()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        continentsData.loadData()
        continentList = continentsData.getContinents()
        
        
        navigationController?.navigationBar.prefersLargeTitles = true
        
        //Data Persistence
//        let app = UIApplication.shared
//
//        NotificationCenter.default.addObserver(self, selector: #selector(ViewController.applicationWillResignActive(_:)), name: NSNotification.Name.UIApplicationWillResignActive, object: app)
//
    }
    
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return continentList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //configure the cell
        let cell = tableView.dequeueReusableCell(withIdentifier: "CountryIdentifier", for: indexPath)
        cell.textLabel?.text = continentList[indexPath.row]
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "countrySegue" {
            let detailVC = segue.destination as! DetailViewController
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)!
            //sets the data for the destination controller
            detailVC.title = continentList[indexPath.row]
            detailVC.continentsData = continentsData
            detailVC.selectedContinent = indexPath.row
        } 
        else if segue.identifier == "continentSegue"{
            let infoVC = segue.destination as! ContinentInfoTableViewController
            let editingCell = sender as! UITableViewCell
            let indexPath = tableView.indexPath(for: editingCell)
            infoVC.name = continentList[indexPath!.row]
            let countryList = continentsData.getCountries(index: (indexPath?.row)!)
            infoVC.number = String(countryList.count)
        }
    }
    
//    @objc func applicationWillResignActive(_notification: NSNotification)
//    {
//        continentsData.writeData()
//    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}

