//
//  GroceryTableViewController.swift
//  GroceryProject
//
//  Created by Karan Motreja on 3/10/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class GroceryTableViewController: UITableViewController {

    var groceries = [Grocery]()
    let dateFormatter = DateFormatter()
    let locale = NSLocale.current
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // set more dateFormatter settings
        dateFormatter.locale = locale
        dateFormatter.dateStyle = .medium
//        dateFormatter.timeStyle = .short
        
        
        if let savedGroceries = loadGroceries()
        {
            groceries += savedGroceries
        }
        
        self.tableView.reloadData()
    }

    
    @IBAction func unwindToGroceryList(sender: UIStoryboardSegue)
    {
        if let sourceView = sender.source as? AddGroceryViewController, let grocery = sourceView.grocery
        {
            let newIndexPath = NSIndexPath(row: groceries.count, section: 0)
            groceries.append(grocery)
            tableView.insertRows(at: [newIndexPath as IndexPath], with: .bottom)
            saveGroceries()
            tableView.reloadData()
        }
    }
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
       
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return groceries.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "groceryCell", for: indexPath)
        
        let grocery = groceries[indexPath.row]
        
        cell.textLabel?.text = grocery.name
        cell.detailTextLabel?.text = "Expires on " + dateFormatter.string(from: grocery.time as Date)
        
        return cell
        
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete
        {
            let toRemove = groceries.remove(at: indexPath.row)
            //toRemove.delete(self)
            
            tableView.deleteRows(at: [indexPath], with: .fade)
            
            saveGroceries()
        }
    }
    
    
    func saveGroceries()
    {
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(groceries, toFile: Grocery.ArchiveURL.path)
        
        if(isSuccessfulSave)
        {
            print("Saved Reminders Successfully!")
        }
        else
        {
            print("Failed to save reminders!")
        }
    }
    
    
    // Persistence
    func loadGroceries() -> [Grocery]?
    {
        return NSKeyedUnarchiver.unarchiveObject(withFile: Grocery.ArchiveURL.path) as? [Grocery]
    }

    
    
    
}
