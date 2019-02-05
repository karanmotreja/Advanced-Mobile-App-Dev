//
//  ArtistAlbums.swift
//  Lab1
//
//  Created by Karan Motreja on 2/4/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation

struct ArtistAlbums: Decodable
{
    let name: String
    let albums: [String]
}
