//
//  DetailViewController.swift
//  Lab2
//
//  Created by Karan Motreja on 2/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class DetailViewController: UITableViewController {
    
    var continentsData = ContinentsDataModelController()
    var selectedContinent = 0
    var countryList = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
       // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    @IBAction func unwindSegue(_segue: UIStoryboardSegue)
    {
        if _segue.identifier == "doneSegue"
        {
            let source = _segue.source as! AddCountryViewController
            
            if source.addedCountry.isEmpty == false
            {
                continentsData.addCountry(index: selectedContinent, newCountry: source.addedCountry, newIndex: countryList.count)
                
                countryList.append(source.addedCountry)
                tableView.reloadData()
            }
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        countryList = continentsData.getCountries(index: selectedContinent)
    }
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return countryList.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "CountryIdentifier", for: indexPath)
        cell.textLabel?.text = countryList[indexPath.row]
        return cell
    }
 

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete
        {
            countryList.remove(at: indexPath.row)
            continentsData.deleteCountry(index: selectedContinent, countryIndex: indexPath.row)
            
            tableView.deleteRows(at: [indexPath], with: .fade)
        }
        else if editingStyle == .insert
        {
            
        }    
    }
    

    
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath)
    {
        let fromRow = fromIndexPath.row
        let toRow = to.row
        let moveCountry = countryList[fromRow]
        
        countryList.swapAt(fromRow, toRow)
        continentsData.deleteCountry(index: selectedContinent, countryIndex: fromRow)
        continentsData.addCountry(index: selectedContinent, newCountry: moveCountry, newIndex: toRow)
        
    }
    

    
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
