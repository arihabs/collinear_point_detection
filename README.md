Problem Statement: Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 points.

Two methods used:
	1. BruteCollinearPoints.java - Brute force method that looks at every distinct combination of 4 points and determines whether they are collinear if they all have same slope to a reference point.
	2. FastCollinearPoints.java - Fast Method that cycles through each point and sorts remaining points based on slope to current point.
